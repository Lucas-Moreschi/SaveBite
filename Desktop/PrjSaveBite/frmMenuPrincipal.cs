using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PrjSaveBite
{
    public partial class frmMenuPrincipal : Form
    {
        private Form activeForm;

        public frmMenuPrincipal()
        {
            InitializeComponent();
            DoubleBuffered = true;
            CompartilhaForm compartilhaForm = new CompartilhaForm();
            compartilhaForm.setMainForm(this);
            OpenChildForm(new frmPrincipal());
        }

        [DllImport("user32.DLL", EntryPoint = "ReleaseCapture")]
        private extern static void ReleaseCapture();
        [DllImport("user32.DLL", EntryPoint = "SendMessage")]
        private extern static void SendMessage(System.IntPtr hWnd, int wMsg, int wParam, int lParam);

        private void btnClose_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void frmMenuPrincipal_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.None;
        }

        public void OpenChildForm(Form childForm)
        {
            CloseChildForm();
            childForm.TopLevel = false;
            childForm.FormBorderStyle = FormBorderStyle.None;
            childForm.Dock = DockStyle.Fill;
            this.panelChildForm.Controls.Add(childForm);
            this.panelChildForm.Tag = childForm;
            childForm.BringToFront();
            childForm.Show();
        }

        private void CloseChildForm()
        {
            if (activeForm != null)
                activeForm.Close();
        }

        private void panel1_MouseDown(object sender, MouseEventArgs e)
        {
            ReleaseCapture();
            SendMessage(this.Handle, 0x112, 0xf012, 0);
        }

        private void lblRetornar_Click(object sender, EventArgs e)
        {
            CompartilhaForm com = new CompartilhaForm();
            frmMenuPrincipal frm = com.getMainForm();
            if (Usuario.Autenticado)
                frm.OpenChildForm(new frmHome());
            else
                frm.OpenChildForm(new frmPrincipal());

            pctRetornar.Visible = false;
            pctRetornar2.Visible = false;

        }

        private void pctRetornar_Click(object sender, EventArgs e)
        {
            CompartilhaForm com = new CompartilhaForm();
            frmMenuPrincipal frm = com.getMainForm();
            frmListaReceitas frmLista = com.getListaReceitas();
            pctRetornar.Visible=false;
            frm.OpenChildForm(frmLista);
        }

        private void pctRetornar2_Click(object sender, EventArgs e)
        {
            CompartilhaForm com = new CompartilhaForm();
            frmMenuPrincipal frm = com.getMainForm();
            frmBuscaReceita frmBusca = com.getBuscaReceita();
            pctRetornar2.Visible = false;
            frm.OpenChildForm(frmBusca);
        }
    }
}
