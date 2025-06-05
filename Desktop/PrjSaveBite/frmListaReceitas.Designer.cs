namespace PrjSaveBite
{
    partial class frmListaReceitas
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
            this.pnlReceitas = new System.Windows.Forms.Panel();
            this.label1 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // pnlReceitas
            // 
            this.pnlReceitas.AutoScroll = true;
            this.pnlReceitas.AutoSize = true;
            this.pnlReceitas.BackColor = System.Drawing.Color.Transparent;
            this.pnlReceitas.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.pnlReceitas.Location = new System.Drawing.Point(431, 265);
            this.pnlReceitas.Margin = new System.Windows.Forms.Padding(4);
            this.pnlReceitas.Name = "pnlReceitas";
            this.pnlReceitas.Size = new System.Drawing.Size(569, 196);
            this.pnlReceitas.TabIndex = 7;
            this.pnlReceitas.Paint += new System.Windows.Forms.PaintEventHandler(this.pnlReceitas_Paint);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.BackColor = System.Drawing.Color.Transparent;
            this.label1.Font = new System.Drawing.Font("Segoe UI", 24F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(151)))), ((int)(((byte)(200)))), ((int)(((byte)(52)))));
            this.label1.Location = new System.Drawing.Point(518, 171);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(392, 54);
            this.label1.TabIndex = 8;
            this.label1.Text = "Receitas encontradas:";
            // 
            // frmListaReceitas
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.GreenYellow;
            this.ClientSize = new System.Drawing.Size(1429, 613);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.pnlReceitas);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "frmListaReceitas";
            this.Text = "frmListaReceitas";
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.frmListaReceitas_Paint);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Panel pnlReceitas;
        private System.Windows.Forms.Label label1;
    }
}