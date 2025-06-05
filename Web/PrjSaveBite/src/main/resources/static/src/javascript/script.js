document.getElementById('open_btn').addEventListener('click', function () {
    document.getElementById('sidebar').classList.toggle('open-sidebar');
});

const sideItems = document.querySelectorAll('#side_items .side-item');

    sideItems.forEach(item => {
        // Adiciona um listener de clique em cada item
        item.addEventListener('click', function() {
            // Remove a classe 'side-item-active' de todos os itens
            sideItems.forEach(i => i.classList.remove('active'));

            // Adiciona a classe 'side-item-active' ao item clicado
            this.classList.add('active');
        });
    });

// Função para carregar páginas dinamicamente
function loadPage(page) {
    const content = document.getElementById('content');
    const xhr = new XMLHttpRequest();
    xhr.open('GET', page, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                content.innerHTML = xhr.responseText;
            } else {
                content.innerHTML = '<p>Erro ao carregar a página.</p>';
                console.error('Erro ao carregar a página: ', xhr.status, xhr.statusText);
            }
        }
    };
    xhr.send();
}

// Função para associar evento de clique a todos os itens da sidebar
function setupSidebar() {
    const sidebarItems = document.querySelectorAll('.side-item'); // Seleciona todos os links da sidebar

    sidebarItems.forEach((item) => {
        item.addEventListener('click', function(event) {
            event.preventDefault(); // Impede o comportamento padrão do link
            
            // Remove a classe 'active' de todos os itens
            sidebarItems.forEach(i => i.classList.remove('active'));
            
            // Adiciona a classe 'active' ao item clicado
            item.classList.add('active');

            // Identifica o item clicado e define a página correspondente
            let page = '';
            const description = item.querySelector('.item-description').textContent.trim();

            switch (description) {
                case 'Home':
                    page = 'inicio.html';
                    break;
                case 'Minha Conta':
                    page = 'usuario.html';
                    break;
                case 'Minhas Receitas':
                    page = 'minhasreceitas.html';
                    break;
                case 'Downloads':
                    page = 'downloads.html';
                    break;
                default:
                    page = 'inicio.html'; // Caso padrão
                    break;
            }

            // Carrega a página correspondente
            loadPage(page);
        });
    });
}

// Chama a função para configurar os eventos da sidebar ao carregar a página
window.onload = setupSidebar;

document.getElementById('open_btn').addEventListener('click', function() {
    const sidebar = document.getElementById('sidebar');
    const content = document.getElementById('content');

    // Alterna a classe 'active' da sidebar
    sidebar.classList.toggle('active');

    // Ajusta o conteúdo com base no estado da sidebar
    if (sidebar.classList.contains('active')) {
        content.classList.remove('content-minimized');
        content.classList.add('content-expanded');
    } else {
        content.classList.remove('content-expanded');
        content.classList.add('content-minimized');
    }
});