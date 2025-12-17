# 💧 Sistema de Hidrômetros e Contas de Água (SHA)

## 📌 Visão Geral

O **SHA – Sistema de Hidrômetros e Contas de Água** é uma aplicação Java em modo console desenvolvida com foco em **boas práticas de engenharia de software** e **padrões de projeto**. O sistema simula o gerenciamento de usuários, hidrômetros e contas de água, incluindo leitura automática de consumo, persistência em arquivos e comunicação entre módulos via Observer.

O projeto foi estruturado para fins **acadêmicos e didáticos**, demonstrando conceitos como **DAO**, **Facade**, **Singleton**, **Observer**, **State** e **Strategy (preparado para expansão)**.


**O projeto está cerca de 70% completo   |xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx---------->**

---

## 🧩 Funcionalidades Principais

### 👤 Usuários

* Cadastro de usuários (CPF, nome e senha)
* Validação de CPF e dados obrigatórios
* Login e logout
* Persistência em arquivo texto
* Remoção de usuário com propagação automática (Observer)

### 🚰 Hidrômetros

* Criação automática de hidrômetros com ID único
* Simulação de leitura periódica em thread
* Limite máximo de leitura com notificação
* Persistência automática
* Remoção com notificação para contas associadas

### 🧾 Contas de Água

* Criação de conta vinculada a **1 usuário + 1 hidrômetro**
* Regra: **um hidrômetro só pode ter uma conta**
* Atualização de leitura
* Remoção automática ao excluir usuário ou hidrômetro

### 🖥️ Painel (CLI)

* Menu dinâmico por estado (pré-inicializado, deslogado, logado)
* Controle central via Fachada
* Tratamento de erros e mensagens amigáveis

---

## 🏗️ Arquitetura e Padrões de Projeto

### Padrões Utilizados

* **Singleton**: Facades, DAOs, Logger
* **Facade**: Interface simplificada para o sistema
* **DAO (Data Access Object)**: Persistência em arquivos
* **Observer**: Sincronização entre Usuário, Hidrômetro e Conta
* **State**: Estados do painel (menus)
* **Thread**: Simulação de leitura contínua de hidrômetros

### Comunicação por Observer

* Remoção de **Usuário** → remove contas associadas
* Remoção de **Hidrômetro** → remove contas associadas

---

## 📁 Estrutura do Projeto

```
SHA/
├── README.md
├── .gitignore
├── configuracoesSistema.properties
├── messages.properties
├── log.txt
└── src/
    ├── src/                 # Núcleo do sistema
    │   ├── FachadaSHA.java
    │   ├── PainelSHA.java
    │   ├── Evento.java
    │   ├── Observer.java
    │   ├── Subject.java
    │   ├── Logger.java
    │   ├── Configuracoes.java
    │   └── Messages.java
    ├── usuario/
    │   ├── Usuario.java
    │   ├── UsuarioDAO.java
    │   ├── UsuarioFacade.java
    │   └── UsuarioException.java
    ├── hidrometroSemOCR/
    │   ├── Hidrometro.java
    │   ├── HidrometroDAO.java
    │   ├── HidrometroFacade.java
    │   └── HidrometroObserver.java
    ├── conta/
    │   ├── Conta.java
    │   ├── ContaDAO.java
    │   └── ContaFacade.java
    ├── estadosDoPainel/
    │   ├── EstadoPainelIF.java
    │   ├── EstadoPreInicializado.java
    │   ├── EstadoDeslogado.java
    │   └── EstadoLogado.java
    └── operacoes/
        └── OperacaoPainel.java
```

---

## ⚙️ Configuração do Sistema

### Arquivo `configuracoesSistema.properties`

Define os caminhos dos arquivos utilizados pelo sistema:

```properties
NomeArquivoDeUsuarios=usuarios.txt
NomeArquivoDeHidrometros=hidrometros.txt
NomeArquivoDeContas=contas.txt
ArquivoDeLog=log.txt
```

### Arquivo `messages.properties`

Centraliza todas as mensagens exibidas ao usuário, facilitando manutenção e internacionalização.

---

## ▶️ Como Executar

### Pré-requisitos

* Java JDK 11 ou superior
* IDE Java (Eclipse, IntelliJ) ou terminal

### Executar via IDE

1. Importe o projeto como **Java Project**
2. Localize a classe:

   ```
   src/PainelSHA.java
   ```
3. Execute o método `main`

---

## 🧪 Persistência

* Todos os dados são salvos automaticamente em **arquivos `.txt`**
* O sistema regrava os arquivos ao encerrar ou fazer logout
* O Logger registra eventos no arquivo configurado

---

## 📚 Objetivo Acadêmico

Este projeto foi desenvolvido com o objetivo de:

* Praticar **orientação a objetos**
* Aplicar **padrões de projeto GoF**
* Simular um sistema real de forma modular e extensível

---

## 🚀 Possíveis Melhorias Futuras

* Interface gráfica (JavaFX ou Swing)
* Banco de dados (JDBC)
* Estratégias de cálculo de tarifa
* Autenticação com hash de senha
* Relatórios de consumo

---

## 👩‍💻 Autoria

Projeto desenvolvido por **Katia Silva**

---

## 📄 Licença

Projeto de uso acadêmico e educacional.
