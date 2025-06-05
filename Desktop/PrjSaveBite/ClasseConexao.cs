using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Data.SqlClient;


    public class ClasseConexao
    {
        SqlConnection conexao = new SqlConnection();

        public SqlConnection conectar(){
            try{
                String strConexao = "Password=123; Persist Security Info=True; User ID=sa; Initial Catalog=ReceitasDB; Data Source=Lucas\\SQLEXPRESS";
                conexao.ConnectionString = strConexao;
                conexao.Open();
                return conexao;
            }catch (Exception){
                desconectar();
                return null;
            }
        }

        public void desconectar(){
            try{
                if ((conexao.State == ConnectionState.Open)){
                    conexao.Close();
                    conexao.Dispose();
                    conexao = null;
                }
            }catch (Exception) { }
        }

        public DataTable executarSQL(String comando_sql){
            try{
                conectar();
                SqlDataAdapter adaptador = new SqlDataAdapter(comando_sql, conexao);
                DataSet ds = new DataSet();
                adaptador.Fill(ds);
                return ds.Tables[0];
            }catch (Exception){
                return null;
            }finally{
                desconectar();
            }
        }

        public DataTable executarSQL(string comando_sql, SqlParameter[] parametros = null)
        { 
        try
        {
            conectar();
            SqlCommand comando = new SqlCommand(comando_sql, conexao);

            // Adiciona os parâmetros ao comando, se houver algum
            if (parametros != null)
            {
                comando.Parameters.AddRange(parametros);
            }

            SqlDataAdapter adaptador = new SqlDataAdapter(comando);
            DataSet ds = new DataSet();
            adaptador.Fill(ds);
            return ds.Tables[0];
        }
        catch (Exception ex)
        {
            return null;
        }
        finally
        {
            desconectar();
        }
    }

    public bool manutencaoDB(String comando_sql) //incluir, alterar, excluir
        {
            try
            {
                conectar();
                SqlCommand comando = new SqlCommand();
                comando.CommandText = comando_sql;
                comando.Connection = conexao;
                comando.ExecuteScalar();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
            finally
            {
                desconectar();
            }
        }//fim do método manutençãoDB

        public int manutencaoDB_Parametros(SqlCommand comando) //incluir, alterar, excluir com parâmetros
        {
            int retorno = 0;
            try
            {
                comando.Connection = conectar();  //adiciona a conexão ao SQLCommand
                retorno = comando.ExecuteNonQuery(); //devolve o número de linhas afetadas no banco
            }
            catch (Exception) { }
            desconectar();
            return retorno;
        }//fim do método manutençãoDB com parâmetros

    public DataTable exSQLParametros(SqlCommand comando)
    {
        DataTable dt = null;
        try
        {
            comando.Connection = conectar();
            SqlDataReader dr = comando.ExecuteReader();
            dt = new DataTable();
            dt.Load(dr);
        }
        catch (Exception) { }
        desconectar();
        return dt;
    }

    public int executa_IncAltExcParametros(SqlCommand comando) //incluir, alterar, excluir
    {
        int retorno = 0;
        try
        {
            comando.Connection = conectar();  //adiciona a conexão ao SQLCommand
            retorno = comando.ExecuteNonQuery(); //devolve o número de linhas afetadas no banco
        }
        catch (Exception e) { Console.WriteLine(e + "adssssssssssssssssssssssssssssssssssssssssss"); }
        desconectar();
        return retorno;
    }

    public DataTable executa_Procedure(String x)
    {
        SqlCommand cmd = new SqlCommand();
        SqlDataAdapter da = new SqlDataAdapter();
        DataTable dt = new DataTable();
        try
        {
            conectar();
            cmd = new SqlCommand("minha_procedure", conectar()); //não digitar Exec, não passar os parâmetros
            cmd.Parameters.Add(new SqlParameter("@par1", x));
            cmd.CommandType = CommandType.StoredProcedure;
            da.SelectCommand = cmd;
            da.Fill(dt);
        }
        catch (Exception) { }
        return dt;
    }


}//fim da classeConexao

