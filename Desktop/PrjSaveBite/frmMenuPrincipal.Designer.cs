namespace PrjSaveBite
{
    partial class frmMenuPrincipal
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmMenuPrincipal));
            this.panel1 = new System.Windows.Forms.Panel();
            this.lblRetornar = new System.Windows.Forms.Label();
            this.btnClose = new System.Windows.Forms.Button();
            this.panelChildForm = new System.Windows.Forms.Panel();
            this.pctRetornar = new System.Windows.Forms.PictureBox();
            this.pctRetornar2 = new System.Windows.Forms.PictureBox();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pctRetornar)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pctRetornar2)).BeginInit();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.White;
            this.panel1.Controls.Add(this.lblRetornar);
            this.panel1.Controls.Add(this.btnClose);
            this.panel1.Controls.Add(this.pctRetornar2);
            this.panel1.Controls.Add(this.pctRetornar);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(1500, 42);
            this.panel1.TabIndex = 0;
            this.panel1.MouseDown += new System.Windows.Forms.MouseEventHandler(this.panel1_MouseDown);
            // 
            // lblRetornar
            // 
            this.lblRetornar.BackColor = System.Drawing.Color.Transparent;
            this.lblRetornar.Font = new System.Drawing.Font("Segoe UI", 20F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblRetornar.Image = ((System.Drawing.Image)(resources.GetObject("lblRetornar.Image")));
            this.lblRetornar.Location = new System.Drawing.Point(13, 5);
            this.lblRetornar.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lblRetornar.Name = "lblRetornar";
            this.lblRetornar.Size = new System.Drawing.Size(36, 33);
            this.lblRetornar.TabIndex = 0;
            this.lblRetornar.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            this.lblRetornar.Click += new System.EventHandler(this.lblRetornar_Click);
            // 
            // btnClose
            // 
            this.btnClose.BackColor = System.Drawing.Color.Transparent;
            this.btnClose.FlatAppearance.BorderSize = 0;
            this.btnClose.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnClose.Font = new System.Drawing.Font("Segoe UI", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnClose.Location = new System.Drawing.Point(1455, 0);
            this.btnClose.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btnClose.Name = "btnClose";
            this.btnClose.Size = new System.Drawing.Size(45, 42);
            this.btnClose.TabIndex = 1;
            this.btnClose.Text = "X";
            this.btnClose.UseVisualStyleBackColor = false;
            this.btnClose.Click += new System.EventHandler(this.btnClose_Click);
            // 
            // panelChildForm
            // 
            this.panelChildForm.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panelChildForm.Location = new System.Drawing.Point(0, 42);
            this.panelChildForm.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.panelChildForm.Name = "panelChildForm";
            this.panelChildForm.Size = new System.Drawing.Size(1500, 661);
            this.panelChildForm.TabIndex = 1;
            // 
            // pctRetornar
            // 
            this.pctRetornar.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pctRetornar.BackgroundImage")));
            this.pctRetornar.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.pctRetornar.Location = new System.Drawing.Point(56, 6);
            this.pctRetornar.Name = "pctRetornar";
            this.pctRetornar.Size = new System.Drawing.Size(36, 30);
            this.pctRetornar.TabIndex = 2;
            this.pctRetornar.TabStop = false;
            this.pctRetornar.Visible = false;
            this.pctRetornar.Click += new System.EventHandler(this.pctRetornar_Click);
            // 
            // pctRetornar2
            // 
            this.pctRetornar2.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pctRetornar2.BackgroundImage")));
            this.pctRetornar2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.pctRetornar2.Location = new System.Drawing.Point(56, 6);
            this.pctRetornar2.Name = "pctRetornar2";
            this.pctRetornar2.Size = new System.Drawing.Size(36, 30);
            this.pctRetornar2.TabIndex = 3;
            this.pctRetornar2.TabStop = false;
            this.pctRetornar2.Visible = false;
            this.pctRetornar2.Click += new System.EventHandler(this.pctRetornar2_Click);
            // 
            // frmMenuPrincipal
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(1500, 703);
            this.Controls.Add(this.panelChildForm);
            this.Controls.Add(this.panel1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Name = "frmMenuPrincipal";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "l";
            this.Load += new System.EventHandler(this.frmMenuPrincipal_Load);
            this.panel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pctRetornar)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pctRetornar2)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button btnClose;
        private System.Windows.Forms.Panel panelChildForm;
        public System.Windows.Forms.Label lblRetornar;
        public System.Windows.Forms.PictureBox pctRetornar;
        public System.Windows.Forms.PictureBox pctRetornar2;
    }
}