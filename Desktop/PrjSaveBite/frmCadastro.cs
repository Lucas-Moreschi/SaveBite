using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PrjSaveBite
{
    public partial class frmCadastro : Form
    {
        public frmMenuPrincipal MainForm { get; set; }

        public frmCadastro()
        {
            InitializeComponent();

        }

        private void FrmLogin_Paint(object sender, PaintEventArgs e)
        {
            Color corInicial = Color.FromArgb(255, 255, 255);
            Color corFinal = Color.FromArgb(152, 203, 52);

            using (LinearGradientBrush brush = new LinearGradientBrush(this.ClientRectangle, corInicial, corFinal, 90F))
            {
                e.Graphics.FillRectangle(brush, this.ClientRectangle);
            }
        }

        private void btnEnviar_Click_1(object sender, EventArgs e)
        {
            string senha = txtSenha.Text;
            string senhaConfirmada = txtConfSenha.Text;

            if (senha != senhaConfirmada)
            {
                MessageBox.Show("As senhas não coincidem. Por favor, verifique e tente novamente.", "Erro de Senha", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            string email = txtEmail.Text;
            string nome = txtNome.Text;
            string senhaHash = HashPassword(senha);

            using (SqlCommand cmd = new SqlCommand("INSERT INTO Usuarios (Nome, Email, SenhaHash, TipoUsuario) VALUES (@Nome, @Email, @SenhaHash, @TipoUsuario)"))
            {
                cmd.Parameters.AddWithValue("@Nome", nome);
                cmd.Parameters.AddWithValue("@Email", email);
                cmd.Parameters.AddWithValue("@SenhaHash", senhaHash);
                cmd.Parameters.AddWithValue("@TipoUsuario", "Usuário");

                ClasseConexao con = new ClasseConexao();
                int linhasAfetadas = con.manutencaoDB_Parametros(cmd);

                if (linhasAfetadas > 0)
                {
                    MessageBox.Show("Usuário cadastrado com sucesso!", "Sucesso", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    FrmLogin childForm = new FrmLogin();
                    MainForm.OpenChildForm(childForm);
                }
                else
                {
                    MessageBox.Show("Erro ao cadastrar usuário.", "Erro", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }

        private void Autenticado(DataTable dt)
        {
            Usuario.Id = Convert.ToInt32(dt.Rows[0]["Id"]);
            Usuario.Email = dt.Rows[0]["Email"] + "";
            Usuario.Nome = dt.Rows[0]["Nome"] + "";
            Usuario.Autenticado = true;
            frmHome childForm = new frmHome();
            MainForm.OpenChildForm(childForm);
        }

        public static string HashPassword(string password)
        {
            try
            {
                using (SHA256 sha256 = SHA256.Create())
                {
                    // Converte a senha para bytes
                    byte[] hashBytes = sha256.ComputeHash(Encoding.UTF8.GetBytes(password));

                    // Converte o hash para uma string hexadecimal
                    StringBuilder hexString = new StringBuilder();
                    foreach (byte b in hashBytes)
                    {
                        hexString.Append(b.ToString("x2"));
                    }

                    return hexString.ToString();
                }
            }
            catch (Exception e)
            {
                throw new Exception("Erro ao gerar o hash da senha.", e);
            }
        }

        private void txtUsuario_Enter(object sender, EventArgs e)
        {
            TextBox txt = sender as TextBox;

            if (Convert.ToInt32(txt.Tag) == 0)
            {
                txt.Text = "";
                txt.ForeColor = SystemColors.WindowText;
                if (txt.Name == "txtSenha" || txt.Name == "txtConfSenha")
                    txt.UseSystemPasswordChar = true;
                txt.Tag = 1;
            }
            return;
        }

        private void txtUsuario_Leave(object sender, EventArgs e)
        {
            TextBox txt = sender as TextBox;

            if (string.IsNullOrWhiteSpace(txt.Text))
            {
                if (txt.Name == "txtEmail")
                    txt.Text = "Email";
                else if (txt.Name == "txtConfSenha")
                {
                    txt.Text = "Confirme sua senha";
                    txt.UseSystemPasswordChar = false;
                }
                else if (txt.Name == "txtNome")
                    txt.Text = "Nome completo";
                else if (txt.Name == "txtSenha")
                {
                    txt.Text = "Senha";
                    txt.UseSystemPasswordChar = false;
                }
                txt.ForeColor = SystemColors.ControlDark;
                txt.Tag = 0;
            }
            else
            {
                return;
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ClasseConexao con = new ClasseConexao();

            con.manutencaoDB($"Insert into Usuarios (Email, SenhaHash) Values ('teste','{HashPassword("123")}')");
        }

        private void FrmLogin_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                btnEnviar_Click_1(sender, e);
            }
        }

        private void frmCadastro_Load(object sender, EventArgs e)
        {
            txtNome.TabIndex = 1;
            txtEmail.TabIndex = 2;
            txtSenha.TabIndex = 3;
            txtConfSenha.TabIndex = 4;
            btnCadastrar.TabIndex = 5;

        }
    }
}