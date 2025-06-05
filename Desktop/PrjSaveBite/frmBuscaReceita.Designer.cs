namespace PrjSaveBite
{
    partial class frmBuscaReceita
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmBuscaReceita));
            this.panelIngredientes = new System.Windows.Forms.Panel();
            this.btnAdicionar = new System.Windows.Forms.Button();
            this.btnBuscar = new System.Windows.Forms.Button();
            this.lblNomeReceita = new System.Windows.Forms.Label();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.panelIngredientes.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // panelIngredientes
            // 
            this.panelIngredientes.BackColor = System.Drawing.Color.Transparent;
            this.panelIngredientes.Controls.Add(this.btnAdicionar);
            this.panelIngredientes.Location = new System.Drawing.Point(27, 87);
            this.panelIngredientes.Margin = new System.Windows.Forms.Padding(4);
            this.panelIngredientes.Name = "panelIngredientes";
            this.panelIngredientes.Size = new System.Drawing.Size(499, 497);
            this.panelIngredientes.TabIndex = 4;
            // 
            // btnAdicionar
            // 
            this.btnAdicionar.AutoSize = true;
            this.btnAdicionar.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(151)))), ((int)(((byte)(200)))), ((int)(((byte)(52)))));
            this.btnAdicionar.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnAdicionar.ForeColor = System.Drawing.Color.White;
            this.btnAdicionar.Location = new System.Drawing.Point(4, 444);
            this.btnAdicionar.Margin = new System.Windows.Forms.Padding(4);
            this.btnAdicionar.Name = "btnAdicionar";
            this.btnAdicionar.Size = new System.Drawing.Size(195, 49);
            this.btnAdicionar.TabIndex = 0;
            this.btnAdicionar.Text = "Adicionar ingrediente";
            this.btnAdicionar.UseVisualStyleBackColor = false;
            this.btnAdicionar.Click += new System.EventHandler(this.btnAdicionarIngrediente_Click);
            // 
            // btnBuscar
            // 
            this.btnBuscar.AutoSize = true;
            this.btnBuscar.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(152)))), ((int)(((byte)(203)))), ((int)(((byte)(52)))));
            this.btnBuscar.FlatAppearance.BorderColor = System.Drawing.Color.White;
            this.btnBuscar.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnBuscar.ForeColor = System.Drawing.Color.WhiteSmoke;
            this.btnBuscar.Location = new System.Drawing.Point(1252, 540);
            this.btnBuscar.Margin = new System.Windows.Forms.Padding(4);
            this.btnBuscar.Name = "btnBuscar";
            this.btnBuscar.Size = new System.Drawing.Size(151, 49);
            this.btnBuscar.TabIndex = 1;
            this.btnBuscar.Text = "Buscar receitas";
            this.btnBuscar.UseVisualStyleBackColor = false;
            this.btnBuscar.Click += new System.EventHandler(this.btnBuscar_Click);
            // 
            // lblNomeReceita
            // 
            this.lblNomeReceita.AutoSize = true;
            this.lblNomeReceita.BackColor = System.Drawing.Color.Transparent;
            this.lblNomeReceita.Font = new System.Drawing.Font("Segoe UI", 9.5F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblNomeReceita.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(152)))), ((int)(((byte)(203)))), ((int)(((byte)(52)))));
            this.lblNomeReceita.Location = new System.Drawing.Point(47, 53);
            this.lblNomeReceita.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lblNomeReceita.Name = "lblNomeReceita";
            this.lblNomeReceita.Size = new System.Drawing.Size(189, 21);
            this.lblNomeReceita.TabIndex = 5;
            this.lblNomeReceita.Text = "Insira seus ingredientes";
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackColor = System.Drawing.Color.Transparent;
            this.pictureBox1.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pictureBox1.BackgroundImage")));
            this.pictureBox1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.pictureBox1.Location = new System.Drawing.Point(611, 87);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(581, 413);
            this.pictureBox1.TabIndex = 6;
            this.pictureBox1.TabStop = false;
            // 
            // frmBuscaReceita
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Linen;
            this.ClientSize = new System.Drawing.Size(1429, 613);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.lblNomeReceita);
            this.Controls.Add(this.btnBuscar);
            this.Controls.Add(this.panelIngredientes);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "frmBuscaReceita";
            this.Padding = new System.Windows.Forms.Padding(27, 25, 27, 25);
            this.Text = "frmIngredientes";
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.frmBuscaReceita_Paint);
            this.panelIngredientes.ResumeLayout(false);
            this.panelIngredientes.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Panel panelIngredientes;
        private System.Windows.Forms.Button btnAdicionar;
        private System.Windows.Forms.Button btnBuscar;
        private System.Windows.Forms.Label lblNomeReceita;
        private System.Windows.Forms.PictureBox pictureBox1;
    }
}