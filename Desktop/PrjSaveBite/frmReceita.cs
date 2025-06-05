using PrjSaveBite.Properties;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PrjSaveBite
{
    public partial class frmReceita : Form
    {
        frmMenuPrincipal MainForm;
        ClasseConexao con;
        DataTable dtReceita;
        int receitaId;


        public frmReceita()
        {
            InitializeComponent();
            CompartilhaForm compartilhaForm = new CompartilhaForm();
            receitaId = compartilhaForm.getIdReceita();
            MainForm = compartilhaForm.getMainForm();
            Mostrar();
        }

        private void Mostrar()
        {
            MostrarIngredientes();
            MostrarModoPreparo();
            MostrarInformacoes();
        }

        private void MostrarIngredientes()
        {
            con = new ClasseConexao();
            dtReceita = con.executarSQL($"SELECT i.Nome AS Ingrediente, ir.Quantidade,\r\nm.Unidade AS Medida\r\nFROM Ingredientes_Receitas ir\r\nJOIN Ingredientes i ON ir.IngredienteId = i.Id\r\nJOIN Medidas m ON ir.MedidaId = m.Id\r\nWHERE ir.ReceitaId = {receitaId}");
            for (int i = 0; i < dtReceita.Rows.Count; i++)
            {
                string s = "";
                if (Convert.ToInt32(dtReceita.Rows[i]["Quantidade"]) > 1 && dtReceita.Rows[i]["Medida"].ToString().Equals("Unidade"))
                    s = "s";
                lstIngredientes.Items.Add(dtReceita.Rows[i]["Ingrediente"] + " " + Convert.ToInt32(dtReceita.Rows[i]["Quantidade"]).ToString("0") + " " + dtReceita.Rows[i]["Medida"] + s);
                lstIngredientes.Items.Add("");
            }
        }

        private void MostrarModoPreparo()
        {
            con = new ClasseConexao();
            dtReceita = con.executarSQL($"Select * from Instrucoes where ReceitaId = {receitaId} order by Etapa");

            for (int i = 0; i < dtReceita.Rows.Count; i++)
            {
                lstModoPreparo.Items.Add(i + " - " + dtReceita.Rows[i]["Descricao"]);
                lstModoPreparo.Items.Add("");
            }
        }

        private void MostrarInformacoes()
        {
            con = new ClasseConexao();
            dtReceita = con.executarSQL($"Select * from Receitas where Id = {receitaId}");
            lblNomeReceita.Text = dtReceita.Rows[0]["Nome"]+"";
            lblDesc.Text = dtReceita.Rows[0]["Descricao"] + "";
            lblTempo.Text = lblTempo.Text.Trim() + " " + dtReceita.Rows[0]["TempoPreparo"]+"min";
            lblPorcoes.Text = lblPorcoes.Text.Trim() + " " + dtReceita.Rows[0]["Porcoes"];
            lblDificuldade.Text = lblDificuldade.Text.Trim() + " " + dtReceita.Rows[0]["Dificuldade"];
            lblAvaliacao.Text = lblAvaliacao.Text.Trim() + " " + dtReceita.Rows[0]["MediaAvaliacao"];

            con = new ClasseConexao();
            dtReceita = con.executarSQL($"SELECT CONVERT(VARCHAR(10), DataCriacao, 103) AS DataFormatada FROM Receitas WHERE Id = {receitaId}");
            lblData.Text = lblData.Text.Trim() + " " + dtReceita.Rows[0]["DataFormatada"];

            con = new ClasseConexao();
            dtReceita = con.executarSQL($"SELECT c.Nome FROM Receitas r JOIN Categorias c ON r.CategoriaId = c.Id WHERE r.Id = {receitaId}");
            lblCategoria.Text = lblCategoria.Text.Trim() + " " + dtReceita.Rows[0]["Nome"];
            con = new ClasseConexao();
            dtReceita = con.executarSQL($"SELECT u.Nome FROM Receitas r JOIN Usuarios u ON r.UsuarioId = u.Id WHERE r.Id = {receitaId}");
            lblCriador.Text = lblCriador.Text.Trim() + " " + dtReceita.Rows[0]["Nome"];
        }
    }
}
