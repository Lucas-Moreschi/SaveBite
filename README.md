# 🧁 SaveBite

**SaveBite** é uma plataforma multifuncional para cadastro, gerenciamento e compartilhamento de receitas culinárias. O projeto está dividido em três versões — **Web**, **Desktop** e **Mobile** —, cada uma com foco e usabilidade específicos.

> ⚠️ **Nota:** Este projeto ainda está em desenvolvimento. Algumas funcionalidades podem estar incompletas ou inativas nas diferentes versões.

---

## 📦 Estrutura do Projeto

```
SaveBite/
├── Web/
├── Desktop/
└── Mobile/
```

---

## 🌐 Versão Web

**Tecnologia:** Java, Spring Boot, Thymeleaf, Maven

### Funcionalidades:
- Cadastro e login de usuários
- Painel do usuário
- Cadastro de receitas
- Edição de receitas
- Visualização detalhada
- Recuperação de senha
- Listagem de receitas salvas

### Como executar:
```bash
mvn spring-boot:run
```

### Estrutura:
- `controller/`, `dto/`, `entity/`, `repository/`, `service/`
- HTML com [Thymeleaf](https://www.thymeleaf.org/)
- CSS, JS e imagens em `resources/static/`
- Templates HTML em `resources/templates/`

---

## 🖥️ Versão Desktop

**Tecnologia:** C# com WinForms (.NET Framework 4.8)

### Funcionalidades:
- Login e cadastro de usuários
- Visualização e busca de receitas
- Compartilhamento de receitas
- Cadastro de novas receitas
- Navegação entre formulários

### Como executar:
1. Abrir o arquivo `PrjSaveBite.sln` no Visual Studio
2. Compilar e executar em modo Debug ou Release

---

## 📱 Versão Mobile

**Tecnologia:** Android (Java)

### Funcionalidades:
- Tela Splash e autenticação
- Cadastro de usuários
- Listagem de receitas
- Detalhes e visualização
- Criação de receitas
- Sessão do usuário e receitas salvas

### Como executar:
1. Abrir o projeto no Android Studio
2. Sincronizar o Gradle
3. Executar em um emulador ou dispositivo físico

---

## 🗄️ Banco de Dados

**Nome:** `ReceitasDB`  
**SGBD:** SQL Server

### Tabelas principais:
- `Usuarios`, `Receitas`, `Categorias`, `Ingredientes`, `Medidas`
- `Ingredientes_Receitas`, `Instrucoes`, `Avaliacoes`

### Triggers:
- Atualização automática da média de avaliações ao **inserir**, **atualizar** ou **excluir** uma avaliação

---

## 📁 Estrutura de Pastas (resumo)

### Web
- `controller/`, `dto/`, `entity/`, `repository/`, `service/`
- HTMLs em `resources/templates/`
- Arquivos estáticos em `resources/static/`

### Desktop
- Formulários: `frmLogin.cs`, `frmCadastro.cs`, `frmReceita.cs`, etc.
- Banco: `ClasseConexao.cs`

### Mobile
- Activities: `MainActivity`, `SaveBiteCadastro`, `ReceitaSelecionada`, etc.
- Layouts XML: `activity_lista_criacao`, `activity_main`, etc.

---

## ⚙️ Tecnologias Usadas

- **Web:** Java 17, Spring Boot, Thymeleaf, Maven
- **Desktop:** C#, WinForms, .NET Framework
- **Mobile:** Java, Android SDK
- **Banco de Dados:** SQL Server

---

## 👥 Créditos

Projeto desenvolvido por Lucas Moreschi e Lucas Laurin, como parte do Trabalho de Conclusão de Curso (TCC) do curso de Desenvolvimento de Sistemas da ETEC de São Paulo - ETESP.

---

## 📄 Licença

Este projeto foi criado como trabalho acadêmico e é disponibilizado para fins educacionais e de aprendizado.
Não é permitida a utilização comercial ou redistribuição sem autorização prévia dos autores.
