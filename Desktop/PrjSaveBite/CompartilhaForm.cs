using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PrjSaveBite
{
    public class CompartilhaForm
    {
        private static frmMenuPrincipal MainForm;
        private static frmListaReceitas listaReceitas;
        private static frmBuscaReceita buscaReceita;
        private static Form anterior;
        private static int IdReceita;

        public void setMainForm(frmMenuPrincipal frm)
        {
            MainForm = frm;
        }

        public frmMenuPrincipal getMainForm()
        {
            return MainForm;
        }

        public void setListaReceitas(frmListaReceitas frm)
        {
            listaReceitas = frm;
        }

        public frmListaReceitas getListaReceitas()
        {
            return listaReceitas;
        }

        public void setBuscaReceita(frmBuscaReceita frm)
        {
            buscaReceita = frm;
        }

        public frmBuscaReceita getBuscaReceita()
        {
            return buscaReceita;
        }

        public void setIdReceita(int NovoId)
        {
            IdReceita = NovoId;
        }

        public int getIdReceita()
        {
            return IdReceita;
        }


    }
}
