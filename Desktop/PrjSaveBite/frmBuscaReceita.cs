using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PrjSaveBite
{
    public partial class frmBuscaReceita : Form
    {
        frmMenuPrincipal MainForm;
        ClasseConexao con;
        private int numeroIngrediente = 1;
        List<string> ingredientes;

        public frmBuscaReceita()
        {
            InitializeComponent();
            CompartilhaForm compartilhaForm = new CompartilhaForm();
            MainForm = compartilhaForm.getMainForm();
            ListaIngredientes();
            AdicionarCampoIngrediente();
        }

        private void ListaIngredientes()
        {
            DataTable dt;
            con = new ClasseConexao();
            ingredientes = new List<string>();

            dt = con.executarSQL("Select Nome from Ingredientes");

            for (int i = 0; i < dt.Rows.Count; i++)
            {
                ingredientes.Add(dt.Rows[i]["Nome"] + "");
            }
        }

        private void btnAdicionarIngrediente_Click(object sender, EventArgs e)
        {
            AdicionarCampoIngrediente();
        }

        private void AdicionarCampoIngrediente()
        {
            AutoCompleteStringCollection autoCompleteCollection = new AutoCompleteStringCollection();
            autoCompleteCollection.AddRange(ingredientes.ToArray());

            
            TextBox novoIngrediente = new TextBox
            {
                Name = "txtIngrediente" + numeroIngrediente,
                BorderStyle = BorderStyle.FixedSingle,
                BackColor = Color.White,
                ForeColor = Color.FromArgb(152, 203, 52),
                Width = 150,
                Location = new Point(10, 30 * (numeroIngrediente - 1)),
                AutoCompleteCustomSource = autoCompleteCollection,
                AutoCompleteMode = AutoCompleteMode.SuggestAppend,
                AutoCompleteSource = AutoCompleteSource.CustomSource,
                Padding = new Padding(10),
                Font = new Font("Arial", 10),
            };

            Button btnRemover = new Button
            {
                Text = "Remover",
                TextAlign = ContentAlignment.MiddleCenter,
                Name = "btnRemoverIngrediente" + numeroIngrediente,
                Font = new Font("Segoe UI", 9, FontStyle.Bold),
                BackColor = Color.FromArgb(152, 203, 52),
                ForeColor = Color.White,
                FlatStyle = FlatStyle.Flat,
                Cursor = Cursors.Hand,
                Location = new Point(170, 30 * (numeroIngrediente - 1) - 2),
                Tag = novoIngrediente
            };

            btnRemover.Click += (s, e) => RemoverCampoIngrediente(novoIngrediente, btnRemover);

            panelIngredientes.Controls.Add(novoIngrediente);
            panelIngredientes.Controls.Add(btnRemover);

            btnAdicionar.Location = new Point(10, 30 * numeroIngrediente);
            panelIngredientes.Controls.Add(btnAdicionar);

            numeroIngrediente++;
            if (numeroIngrediente >= 14)
                btnAdicionar.Visible = false;
        }

        private void RemoverCampoIngrediente(TextBox ingrediente, Button btnRemover)
        {
            panelIngredientes.Controls.Remove(ingrediente);
            panelIngredientes.Controls.Remove(btnRemover);

            if (numeroIngrediente <= 14)
                btnAdicionar.Visible = true;
            ReorganizarCampos();
            this.Invalidate();
        }

        private void ReorganizarCampos()
        {
            int index = 0; 

            List<Control> controlesOrdenados = new List<Control>();

            foreach (Control control in panelIngredientes.Controls)
            {
                if (control is TextBox || (control is Button btn && btn.Text == "Remover"))
                {
                    controlesOrdenados.Add(control);
                }
            }

            panelIngredientes.Controls.Clear();

            List<Control> textBoxes = controlesOrdenados.Where(c => c is TextBox).OrderBy(c => c.Top).ToList();
            List<Control> buttons = controlesOrdenados.Where(c => c is Button).OrderBy(c => c.Top).ToList();

            foreach (var textBox in textBoxes)
            {
                textBox.Location = new Point(10, 30 * index);
                panelIngredientes.Controls.Add(textBox);

                var btnRemover = buttons.FirstOrDefault(btn => btn.Tag == textBox);
                if (btnRemover != null)
                {
                    btnRemover.Location = new Point(170, 30 * index - 2);
                    panelIngredientes.Controls.Add(btnRemover);
                }

                index++;
            }

            btnAdicionar.Location = new Point(10, 30 * index);
            panelIngredientes.Controls.Add(btnAdicionar);

            numeroIngrediente = index;
        }

        private void btnBuscar_Click(object sender, EventArgs e)
        {
            List<string> ingredientes = new List<string>();
            foreach (Control control in panelIngredientes.Controls)
            {
                if (control is TextBox)
                {
                    TextBox txtIngrediente = (TextBox)control;
                    if (!string.IsNullOrWhiteSpace(txtIngrediente.Text) && ChecarString(txtIngrediente.Text.Trim()))
                        ingredientes.Add(txtIngrediente.Text.Trim());
                }
            }

            BuscarReceitasPorIngredientes(ingredientes);
        }

        private bool ChecarString(string text)
        {
            for (int i = 0; i < ingredientes.Count; i++)
            {
                if (text.ToLower().Equals(ingredientes[i].ToLower()))
                {
                    return true;
                }

            }
            return false;
        }

        private void BuscarReceitasPorIngredientes(List<string> ingredientes)
        {
            con = new ClasseConexao();

            if (ingredientes.Count == 0)
            {
                MessageBox.Show("Por favor, insira pelo menos um ingrediente para buscar.");
                return;
            }

            string query = @"
            SELECT DISTINCT r.Nome, r.Id 
            FROM Receitas r
            INNER JOIN Ingredientes_Receitas ir ON r.Id = ir.ReceitaId
            INNER JOIN Ingredientes i ON ir.IngredienteId = i.Id
            WHERE " + string.Join(" OR ", ingredientes.Select((ing, index) => $"i.Nome LIKE @Ingrediente{index}"));

            List<SqlParameter> parametros = ingredientes
                .Select((ing, index) => new SqlParameter($"@Ingrediente{index}", "%" + ing + "%"))
                .ToList();

            DataTable dtReceitas = con.executarSQL(query, parametros.ToArray());
            CompartilhaForm comp = new CompartilhaForm();
            comp.setBuscaReceita(this);
            MainForm.pctRetornar2.Visible = true;
            MainForm.OpenChildForm(new frmListaReceitas(dtReceitas));
        }

        private void frmBuscaReceita_Paint(object sender, PaintEventArgs e)
        {
            Color corInicial = Color.FromArgb(255, 255, 255);
            Color corFinal = Color.FromArgb(152, 203, 52);

            using (LinearGradientBrush brush = new LinearGradientBrush(this.ClientRectangle, corInicial, corFinal, 90F))
            {
                e.Graphics.FillRectangle(brush, this.ClientRectangle);
            }
        }
    }
}
