# Teste Para Estágio DTI

**Erick Etiene Simião**

**Linguagem desenvolvida: Java - Aplicação mobile Android**

## Instruções para executar a aplicação
É possível simplesmente fazer um fork do projeto e abrí-lo no Android Studio e dar play no módulo app, respeitando os critérios de estrutura do projeto:
Versão Gradle: 7.3.1
API minima do dispositivo: 29 (Android 10)
compileSDK: 33 (Tiramisu)
targetSDK: 32 (Android 12L)
no entanto, aqui no github está disponibilizado o APK da aplicação para ser instalada em qualquer dispositivo android que seja superior ao android 10.

## Premissas assumidas
Projetar um sistema de uma estrutura de dados de lembretes em um aplicativo mobile com CRUD simples, 
porém com boa qualidade de código, boas práticas de desenvolvimento, e com a criação de testes.

## Critérios cumpridos
Desenvolvi um código Android Mobile em Java separando a responsabilidade das classes em padrão de arquitetura MVC, criando
bons nomes de variáveis e métodos, aplicando orientação à objetos. 
No entanto, há pontos a destacar que poderiam ter sido melhores implementados:
1- Moduralização da funcionalidade de remover um lembrete da lista. - Toda vez que um ítem vai ser removido, é preciso criar a funcionalidade de novo, sendo mais simples criar um método
2- A implementação do Design Pattern Singleton para as estruturas de dados que definem as listas (DateList, ReminderGroup, Reminder)
3- Melhor modularização da MainActivity, separando melhor as responsabilidades.

Utilização de XML semântico para construção da interface, utilizando RelativeLayout e MaterialDesignComponents

Testes automatizados unitários e instrumentais para todos os métodos do projeto, salvo alguns getters e setters de variáveis.


## Decisões de Projeto
A melhor forma de averiguar minhas decisões é por meio dos meus Commit feitos através do Git.

Minha primeira decisão foi **implementar a interface inicial**, 
implementando apenas 3 classes:
#### MainActivity: Responsável pela captura das ações do usuário durante o uso da aplicação ;
#### Reminder: Classe modelo para definir o que seria um Lembrete ;
#### ReminderAdapter: Adaptador que lida com a mudança do estado da lista de lembretes com a exibição na tela;
Nesse momento, imaginei que talvez o padrão MVVM pudesse ser mais interessante por como foi modelada a estrutura inicial, porém mais adiante o MVC pareceu mais adequado.
Não tive nenhum problema nessa parte do processo, sendo completada no começo do meu primeiro dia de trabalho.

Minha segunda decisão foi **a finalização da interface**, 
ao modificar o componente ListView para ExpandableListView.
Como eu não conhecia o componente, precisei gastar um tempo de estudo sobre o funcionamento para conseguir usar ele no meu Adapter, então precisei alterar a lógica de funcionamento do sistema pra interface funcionar bem.

Minha terceira decisão foi inserir a **implementação da remoção de um item da lista**, 
porém houve um bug causado pela ordenação dos itens por data, atrapalhando a remoção.
Foi necessário pesquisar uma forma de resolver, porém futuramente isso se mostrou desnecessário após a correção de utilização das estruturas, onde corrigiu a posição dos itens automaticamente.

Minha quarta decisão foi **implementar a refatoração do meu código**, foi onde meus problemas começaram.
Primeiro separei a funcionalidade de ordenação de um lembrete em uma classe exclusíva.
Depois, separei as responsabilidades de data para uma classe exclusiva, ReminderDataManager, responsável por lidar com as estruturas de dados implementadas.
Nesse momento, passei por algumas dificuldades. Apesar do meu código funcionar bem, todas as responsabilidades estavam muito centralizadas, 
e eu percebi o quanto eu era iniciante nesse momento, tendo dificuldade com coisas que não imaginei que seriam complicadas.
Construi um ReminderViewHolder para lidar com as ações de cada componente da lista, no fim das contas me tomou um dia inteiro de trabalho, 
algo que eu pensei que conseguiria fazer em poucas horas, no entanto o processo de aprendizado que tive nesse momento foi extremamente enriquecedor.

Por fim, minha ultima decisão de projeto foi **a implementação de testes** no meu código.
Nesse momento, eu precisei optar em se eu faria uma API, ou implementaria meus testes.
Eu sabia que poderia implementar uma API, pois é algo que eu já sabia fazer, e já tenho em alguns projetos, mas pensei que fazer uma API e não entregar testes de nenhum dos dois seria o pior cenário.
nos meus cursos, por incrível que pareça, estou exatamente no momento em que eu devia estar estudando sobre testes,
![img.png](img.png)
mas apesar de fazer testes em algumas atividades, percebi o quão diferente é criar testes pra um cenário real em que eu mesmo estou desenvolvendo. Eu sabia que tomaria um tempo enorme fazer isso, 
então tive que optar entre os dois pra ser capaz de entregar algo bem feito, no lugar de algo completo e feito de qualquer forma.

Grande parte do que apliquei nos testes eu tive que aprender do zero, da pra identificar isso com o gap de tempo entre meus commits, esse foi o que mais me custou, 
isso porque eu passei a maior parte do tempo estudando e pesquisando sobre como aplicar o teste que eu queria fazer. Foram 2 dias de implementação de testes,
e tanto quanto foi frustrante, foi um alívio extremo conseguir fazer todos os testes que eu planejei fazer.

Graças a ele, eu identifiquei diversos bugs no meu projeto, e tive que acabar alterando meu código original, o que me custou um bom tempo pra identificar cada problema.
Não imaginei que isso fosse engrandecer tanto o meu projeto, me fez identificar bugs que eu nem mesmo tinha notado, e seria capaz de entregar o projeto sem perceber que eles estavam lá.
Com certeza valeu a pena optar por implementar esses testes;


## Feedback do processo
Um grande problema do programador que está começando é a falta de projetos,e essa foi uma ótima experiência que eu tive. 
A grande diferença dela para meus outros projetos,foi o primeiro em que eu tive um prazo para completar, 
mas como sempre tinha conteúdo novo a buscar aprender, isso é algo que enriquece muito, simplesmente participar do processo, me torna um desenvolvedor melhor.
Há muitas coisas que eu gostaria de ter feito melhor, ou que eu queria ter feito a mais, e eu sei que posso simplesmente fazer isso, 
não me limitar por essa entrega, pois é um projeto que me colocou muito à prova, e pretendo continuar implementando melhorias nele em prol do meu crescimento.
Dito isso, obrigado pela oportunidade!
