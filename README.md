# FirefoxSync

Repositório do trabalho acadêmico sobre Criptografia Simétrica, Derivação de chaves e Autenticação para a disciplina de Segurança da Informação e de Redes do curso de Bacharelado em Sistemas de Informação da Universidade Federal de Santa Catarina.

Foi baseado no projeto do serviço SYNC (https://hacks.mozilla.org/2018/11/firefox-sync-privacy/) do browser Mozilla Firefox, utilizando as bibliotecas criptográficas fornecidas pelo provedor Bouncy Castle.
Utilizado o provedor BCFIPS que é a versão FIPS da Bouncy Castle.

No serviço Sync, as senhas e os favoritos são armazenados no servidor e podem ser acessados de qualquer dispositivo.
O serviço Sync não armazena nenhuma informação sobre chaves criptográficas usadas para cifrar os dados dos clientes.
A aplicação simula o cliente interagindo com o servidor Sync conforme a figura abaixo
<img src="https://2r4s9p1yi1fa2jd7j43zph8r-wpengine.netdna-ssl.com/files/2018/11/Sync-Blogpost.png">

# A aplicação contém as seguites funcionalidades:

1) Cadastrar dados do cliente;
2) Cadastrar contas de clientes no servidor Sync;
4) Armazenar dados do cliente cifrados no servidor Sync;
5) Obter dados do servidor Sync e mostrar dados decifrados na tela do cliente.

# Detalhes técnicos:

Utilizado PBKDF2 com 1000 iterações para derivar senha em um token de autenticação
