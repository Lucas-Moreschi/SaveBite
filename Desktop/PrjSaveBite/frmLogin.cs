using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PrjSaveBite
{
    public partial class FrmLogin : Form
    {
        public frmMenuPrincipal MainForm { get; set; }

        public FrmLogin()
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
            string email = txtUsuario.Text;
            string senhaHash = HashPassword(txtSenha.Text);
            Console.WriteLine(senhaHash);
            ClasseConexao con = new ClasseConexao();
            DataTable dt = con.executarSQL($"Select * from Usuarios where Email like '{email}' and SenhaHash = '{senhaHash}'");
            if (dt.Rows.Count >= 1)
                Autenticado(dt);
            else
                MessageBox.Show("Usuário ou senha incorretos", "Erro de autenticação", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }

        private void Autenticado(DataTable dt)
        {
            CompartilhaForm com = new CompartilhaForm();
            MainForm = com.getMainForm();
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
                if (txt.Name == "txtSenha")
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
                txt.Text = "Email";
                txt.ForeColor = SystemColors.ControlDark;
                txt.Tag = 0;
            }
            else
            {
                return;
            }
        }

        private void txtSenha_Leave(object sender, EventArgs e)
        {
            TextBox txt = sender as TextBox;

            if (string.IsNullOrWhiteSpace(txt.Text))
            {
                txt.Text = "Senha";
                txt.ForeColor = SystemColors.ControlDark;
                txt.Tag = 0;
                txt.UseSystemPasswordChar = false;
            }
        }

        private void FrmLogin_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                btnEnviar_Click_1(sender, e);
            }
        }
    }
}