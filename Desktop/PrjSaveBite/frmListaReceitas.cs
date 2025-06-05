using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PrjSaveBite
{
    public partial class frmListaReceitas : Form
    {
        DataTable receitas;
        ClasseConexao con;
        frmMenuPrincipal MainForm;
        CompartilhaForm compartilhaForm;

        public frmListaReceitas(DataTable _receitas)
        {
            InitializeComponent();
            compartilhaForm = new CompartilhaForm();
            MainForm = compartilhaForm.getMainForm();
            receitas = _receitas;
            DisplayReceitas();
            
        }

        private void DisplayReceitas()
        { 
            if (receitas.Rows.Count > 0)
            {
                pnlReceitas.Controls.Clear();
                int yPosition = 10;
                Font fonte = new Font("Segoe UI", 12);

                foreach (DataRow row in receitas.Rows)
                {
                    Label lblNome = new Label
                    {
                        Text = row["Nome"].ToString(),
                        Font = fonte,
                        Location = new System.Drawing.Point(10, yPosition),
                        AutoSize = true
                    };

                    Button btnVer = new Button
                    {
                        Text = "Ver",
                        FlatStyle = FlatStyle.Flat,
                        BackColor = Color.FromArgb(151, 200, 52),
                        Location = new System.Drawing.Point(250, yPosition),
                        Tag = row["Id"]
                    };
                    btnVer.FlatAppearance.BorderSize = 0;
                    btnVer.Click += BtnVer_Click;

                    pnlReceitas.Controls.Add(lblNome);
                    pnlReceitas.Controls.Add(btnVer);

                    yPosition += 30;
                }
            }
            else
            {
                Label lblNenhumaReceita = new Label();
                lblNenhumaReceita.Text = "Nenhuma receita encontrada com os ingredientes especificados.";
                lblNenhumaReceita.Location = new Point(10, 10);
                lblNenhumaReceita.AutoSize = true;
                pnlReceitas.Controls.Add(lblNenhumaReceita);
            }
        }

        private void BtnVer_Click(object sender, EventArgs e)
        {
            compartilhaForm = new CompartilhaForm();
            Button btn = sender as Button;
            int receitaId = (int)btn.Tag;
            compartilhaForm.setIdReceita(receitaId);
            compartilhaForm.setListaReceitas(this);
            MainForm.pctRetornar.Visible = true;
            MainForm.pctRetornar.BringToFront();
            MainForm.OpenChildForm(new frmReceita());
        }

        private void frmListaReceitas_Paint(object sender, PaintEventArgs e)
        {
            Color corInicial = Color.FromArgb(255, 255, 255);
            Color corFinal = Color.FromArgb(152, 203, 52);

            using (LinearGradientBrush brush = new LinearGradientBrush(this.ClientRectangle, corInicial, corFinal, 90F))
            {
                e.Graphics.FillRectangle(brush, this.ClientRectangle);
            }
        }

        private void pnlReceitas_Paint(object sender, PaintEventArgs e)
        {
            
        }
    }
}
