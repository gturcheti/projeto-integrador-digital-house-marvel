# :iphone: Bem vindo ao DH Heroes app  !

Trata-se de um Projeto Integrador (PI) para conclusão do curso de "Desenvolvedor Mobile Android" da Digital House, que tem por objetivo aplicar na prática os conceitos teóricos vistos nas aulas ao longo do curso.


## :blue_book: Proposta do projeto
 
O projeto integrador é colaborativo, focado na aprendizagem, devendo obedecer os requisitos mínimos da Digital House, retornando aos participantes um gitportfolio, contribuindo para o ingresso no mercado de trabalho. 

### :heavy_check_mark: Regras do jogo:
Os requisitos mínimos para aprovação do projeto são:
- Componentes de navegação;
- Conteúdo dinâmico por meio do consumo de ao menos uma API;
- Conteúdo offline;
- Autenticação de usuários;
- Compartilhamento em redes sociais;
- Seguir o padrão de arquitetura MVVM;
- Código documentado;

## :bulb: A ideia do "DH Heroes" app!

Para cumprir os requisitos do projeto o grupo decidiu por consumir a API da Marvel, disponibilizando ao usuário a possiblidade explorar o conteúdo do "universo Marvel".

**Funcionalidades do aplicativo:**
- Busca dos heróis (conteúdo dinâmico);
- Salvá-los como favoritos e disponibilizá-los offline;
- Compartilhá-los às redes sociais;
- Consultar à lista de "revistinhas" desse herói;

**Recursos e padrões utilizados**
- Linguagem de programação: Kotlin;
- Ide utilizada: Android Studio;
- Padrão de arquitetura MVVM;
- Android Jetpack Navigation;
- Componentes do Material Design;
- Android Room: persistência de dados;
- Operações assíncronas com Kotlin Coroutines e Kotlin Flow;

## :hammer_and_wrench: Configurando o código em seu Android Studio

Considerando que a proposta do aplicativo é acadêmica,  o código foi disponibilizado no github. Porém para instalar a aplicação em seu "Android Studio" é necessário configurar alguns itens.

### :clapper: Marvel API
Para configurar a API será necessário que acesse o site oficial da Marvel e veja a [documentação](https://developer.marvel.com/documentation/getting_started) 

Faça seu cadastro, siga as orientações e colete a chave pública e privada.
Inclua no "gradle.properties" as chaves com as seguintes TAGS: 
```   
MARVEL_API_PUBLICKEY = "informe sua chave pública"  
MARVEL_API_PRIVATEKEY = "informe sua chave privada"
```
O restante já está configurado, dessa forma você conseguirá consumir a API por meio de suas chaves.

### :fire: Firebase 
Por conta dos recursos utilizados do Firebase (Auth e Realtime Database), será necessário criar uma conta no google e configurar o SDK do Firabase com os seus dados. O próprio google irá fornecer o arquivo de configuração "google-services.json". 

Faça o download do arquivo, mude para a visualização do **Projeto** no Android Studio para ver o diretório raiz e mova o arquivo google-services.json, que você acabou de salvar, para o diretório raiz do módulo do app Android sobrescrevendo o arquivo original.

Siga as intruções na [documentação](https://firebase.google.com/docs/android/setup?authuser=7&hl=pt)

Agora será possível clonar a aplicação e ter acesso a todos os recursos do app, bem como o seu próprio "painel" do firebase.

### :vertical_traffic_light: Permissões
Verifique no arquivo "Manifest" se a permissão de acesso a internet está devidamente configurada. 

```   

	<uses-permission android:name="android.permission.INTERNET"/>

```

## :books: Libraries e dependências do android

Foi necessário a utilização de algumas bibliotecas externas e nativas para a aplicação, bem como declarar as dependências no "gradle". Veja abaixo.

Bibliotecas externas
:  [Retrofit](https://square.github.io/retrofit/) - para o consumo da api;
[OkHttp](https://square.github.io/okhttp/) - para obter os logs da comunicação da api via http;
[Coil](https://coil-kt.github.io/coil/) - para a visualização das imagens por meio das url's retornadas da api;

Recursos do Android
: Room e DataStore: persistência de dados;
ViewModel e LiveData: recursos utilizados para arquitetura MVVM;
Navigation e Fragment-ktx: dependências para uso do Android Jetpack Navigation;
Coroutines (Kotlin) : operações assíncronas;
BoM Firebase, GoogleAuth e Realtime Database: recursos do Firebase.

## :iphone: UI's do App

### :house: MainActivity
![MainActivity](https://user-images.githubusercontent.com/94029140/180088226-2bae8503-0c8d-49b9-b0cc-6977906b90ef.jpg)

É a tela principal do app. 

O código verifica se há usuário autenticado, caso não possua é feito automaticamente o redirecionamento para a tela de autenticação. 

No canto superior direito há a opção do usuário autenticado "sair", uma vez clicado no botão o registro de autenticação é removido e o usuário é redirecionado para a tela de login.

O botão "explore" direciona o usuário à interface de heróis.

---

###  :lock: LoginActivity

**Login Fragment**

![LoginFragment](https://user-images.githubusercontent.com/94029140/180088230-9de52fd8-8e93-4b24-9831-ddf991626156.jpg)


**RegisterFragment**

![RegisterFragment](https://user-images.githubusercontent.com/94029140/180088232-927925d9-4b59-486c-bf22-33e1dd9dedef.jpg)

---

### :film_projector: HeroActivity

**:mag_right: SearchView: HeroiListFragment**

![HeroList](https://user-images.githubusercontent.com/94029140/180088234-d8911874-8dfa-4bb5-90b4-aa5c8d227c5f.jpg)

É a interface que disponibiliza ao usuário o conteúdo da API da Marvel. É uma única activity que recebe um "nav_graph" responsável por gerenciar três "fragments". 

O primeiro "fragment" recebe um "SearchView", em que o usuário realiza a busca de um herói por meio do nome, o conteúdo é disponibilizado na "RecyclerView" e mais informações daquele Herói pode ser acessado com um clique sobre o item. 


**Tela do Herói: HeroViewPagerFragment**

![HeroBiography](https://user-images.githubusercontent.com/94029140/180088237-f1ecccf9-137b-4ffb-8d3f-2f1ed2577f52.jpg)

É uma interface que recebe um "ViewPager" e um "TabLayout", servindo de container para dois fragmentos, o primeiro com conteúdo da biografia do herói (conforme imagem) e o segundo com a lista de quadrinhos em que aquele herói teve participação. 

O recurso de "ViewPager" e de "TabLayout", permite a alternância de interfaces por meio do deslize de tela bem como do click na "tab" desejada.  

O fragmento "biography" fornece as informações de nome e descrição do herói, além de possuir dois botões, um no canto superior direito com a opção de compartilhar o herói nas redes sociais, e um botão inferior permitindo o acesso ao site da Marvel para mais informações. 

**ComicsFragment**

![enter image description here](https://user-images.githubusercontent.com/94029140/180088238-8729d2e7-e0d5-40fb-86f7-91d99b7118f9.jpg)

O fragmento "comics" fornece uma lista de quadrinhos, com a opção de clique que direciona ao site da Marvel para mais informações.

Os links externos são sempre precedidos por um "Alert Dialog" avisando que o link será aberto no navegador do sistema operacional.



## :pushpin: Possíveis melhorias

Segue algumas dicas do que pode ser aprimorado:
- a implementação de um tema noturno;
- o "favoritos" pode ser aprimorado para um recurso em nuvem em vez de local;
- a lista de busca dos heróis pode ser aprimorada com mais opções de filtro e seleção;


## :man_office_worker: Autores do projeto
- [Gabriel Saraiva Turcheti](https://www.linkedin.com/in/gturcheti/)
- [Emanuel Filipe Gregui Mendes](http://www.linkedin.com/in/emanuel--filipe)
- José Ivaldo Junior,
- Washington 

---
