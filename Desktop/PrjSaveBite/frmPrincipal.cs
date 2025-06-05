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
    public partial class frmPrincipal : Form
    {
        frmMenuPrincipal MainForm;
        public frmPrincipal()
        {
            InitializeComponent();
            CompartilhaForm compartilhaForm = new CompartilhaForm();
            MainForm = compartilhaForm.getMainForm();
            
        }

        private void btnLogin_Click(object sender, EventArgs e)
        {
                FrmLogin childForm = new FrmLogin();
                childForm.MainForm = MainForm;
                MainForm.OpenChildForm(childForm);  
        }

        private void frmPrincipal_Paint(object sender, PaintEventArgs e)
        {
            Color corInicial = Color.FromArgb(255, 255, 255);
            Color corFinal = Color.FromArgb(152, 203, 52);

            using (LinearGradientBrush brush = new LinearGradientBrush(this.ClientRectangle, corInicial, corFinal, 90F))
            {
                e.Graphics.FillRectangle(brush, this.ClientRectangle);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            frmCadastro childForm = new frmCadastro();
            childForm.MainForm = MainForm;
            MainForm.OpenChildForm(childForm);
        }
    }
}
