using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PrjSaveBite
{
    public class Usuario
    {
        private static int id;
        private static string nome;
        private static string email;
        private static bool autenticado;

        public static int Id
        {
            get { return id; }
            set { id = value; }
        }

        public static string Nome
        {
            get { return nome; }
            set { nome = value; }
        }

        public static string Email
        {
            get { return email; }
            set { email = value; }
        }

        public static bool Autenticado
        {
            get { return autenticado; }
            set { autenticado = value; }
        }
    }
}
