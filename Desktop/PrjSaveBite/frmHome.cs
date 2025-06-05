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
    public partial class frmHome : Form
    {
        frmMenuPrincipal MainForm;

        public frmHome()
        {
            InitializeComponent();
            CompartilhaForm compartilhaForm = new CompartilhaForm();
            MainForm = compartilhaForm.getMainForm();
        }

        private void btnEncontrar_Click(object sender, EventArgs e)
        {
            frmBuscaReceita childForm = new frmBuscaReceita();
            MainForm.OpenChildForm(childForm);
        }

        private void btnCriar_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Esta funcionalidade está em desenvolvimento.", "Em Desenvolvimento", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void frmHome_Paint(object sender, PaintEventArgs e)
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
