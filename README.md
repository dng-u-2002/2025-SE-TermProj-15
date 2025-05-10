# Software Engineering - Spring 2025  
## 윷놀이 Project  

This repository contains the **윷놀이 Game Project** for the Software Engineering course (Spring 2025). The project applies **Object-Oriented Analysis and Design (OOAD)** principles to develop a digital version of the traditional Korean board game *윷놀이*.  

## 📌 Project Overview  
- Develop a **윷놀이 game** using OOAD techniques.  
- Players can specify the **number of participants** (2 to 4) and **number of game pieces** (2 to 5).  
- The game board supports **custom board shapes**:  
  - Rectangle (사각형)  
  - Pentagon (오각형)  
  - Hexagon (육각형)  
- Each board layout includes its own **movement rules**, including:  
  - **Branching path selection at the center node**, based on whether the player stops at the center or not.  
- Players take turns using:  
  - **"Random Throw" button** for random results  
  - **"Manual Throw" button** to select specific results for testing  
- The game supports:  
  - **Piece grouping (말 업기)**  
  - **Capturing opponents’ pieces**  
  - **Winning condition**: The first player to move all pieces to the goal wins  
- Players can **restart** or **exit** after a game finishes  

### 🧭 Pentagon and Hexagon Movement Examples  
오각형과 육각형에서의 경로 선택 예시는 다음과 같습니다:

#### ▶ Pentagon Board Path Example  
![Pentagon Path](Pentagon_logic.jpg)

#### ▶ Hexagon Board Path Example  
![Hexagon Path](Hexagon_logic.jpg)

## 📅 Project Timeline  
### ✅ First Due (5th April)  
- Create a **GitHub project page** and post it on the e-class project board.  

### ✅ Mid Due (10th May)  
- Submit a **fully functional version** using **Java Swing**.  
- Include:  
  - All **source code** and **data files**  
  - **Executable file**  
  - **Gameplay demonstration video**  

### ✅ Final Due (Late May)  
- Add explanations on **UI modification impacts** and provide **two UI demonstration videos**.  
- Implement **two different UI toolkits** (e.g., Swing, JavaFX, Eclipse SWT).  
- Submit:  
  - All **source code** and **data files**  
  - **Project documentation**, including:  
    - Project Overview  
    - Use Case Model  
    - Design & Implementation Report  
    - Testing Report  
    - GitHub Project Report  

### 🎤 Presentation Day (Early June)  
- Evaluation based on:  
  - **Functionality**  
  - **Project documentation**  
  - **Final presentation**  

## 🛠 Development Guidelines  
- Follow **OOAD principles** and document design decisions.  
- Implement using **MVC architecture** (separating UI and logic).  
- Support **multiple UI toolkits** to demonstrate modularity.  
- Ensure **JUnit testability** for core logic.  
- Enable **custom board shapes** (e.g., pentagon, hexagon).  

## 📂 Repository Structure  
```
📁 .idea/
 ┣ 📄 .gitignore
 ┣ 📄 material_theme_project_new.xml
 ┣ 📄 misc.xml
 ┣ 📄 modules.xml
 ┗ 📄 vcs.xml

📁 yoot/
 ┗ 📁 .idea/
   ┣ 📄 .gitignore
   ┣ 📄 .name
   ┣ 📄 misc.xml
   ┣ 📄 modules.xml
   ┣ 📄 uiDesigner.xml
   ┗ 📄 vcs.xml

📁 src/
 ┗ 📁 yutnori_ver2/
   ┣ 📄 BoardPanel.java
   ┣ 📄 Game.java
   ┣ 📄 Main.java
   ┣ 📄 Player.java
   ┣ 📄 RuleEngine.java
   ┣ 📄 StartFrame.java
   ┣ 📄 Yut.java
   ┣ 📄 YutResult.java
   ┣ 📄 YutResultType.java
   ┣ 📄 YutScreen.java
   ┣ 📄 YutThrower.java
   ┗ 📄 YutNori.exe

📄 .gitignore
📄 yoot.iml
📄 .classpath
📄 .project
📄 2025-SE-TermProj-15.iml
📄 README.md
```

## 역할분담  
- 문서화 및 UML: 김영준, 양희옥  
- UI 개발: 이규빈, 하동윤  
- Logic 개발: 김동우, 하동윤

## 1차 데드라인  
- 5/3 UML 작성 완료  
- 5/8 Java Swing 구현 완료
- 5/10 제출
