# JavaFX RPS Implementation Plan (Level 1 -> Level 3)

## 1. Muc tieu cua tai lieu
Tai lieu nay la checklist thuc thi tung buoc cho project `Assignment3-RPS`, de di tu console app hien tai sang GUI JavaFX theo yeu cau assignment:

1. Co GUI cho game RPS.
2. Co simple machine learning.
3. Luu frequency table ra file.
4. Co menu `About`, `Exit`, `Start a new game`.
5. Mac dinh 20 rounds, cho phep doi so rounds truoc game tiep theo.
6. Viet report giai thich event, callback, va code reuse voi inversion of control.

## 2. Hien trang project
Project hien da co logic game console trong `src/rps`:

1. `Game`, `Rules`, `StandardRules`, `Scoreboard`, `RoundResult`.
2. `MachineLearningComputerStrategy` co load/save frequency table.
3. Test JUnit da co cho core logic.

Dieu nay co nghia la chung ta khong can viet lai game logic tu dau; chu yeu can thay lop giao tiep console bang giao tiep JavaFX.

## 3. ELI5 (giai thich rat de hieu)
Hinh dung game la mot nha hang:

1. Bep (logic game) da nau ngon san.
2. Ban order cu (console) la nhap so trong terminal.
3. Assignment nay yeu cau doi thanh quan order dep (JavaFX GUI).
4. Muc tieu la doi quan order, khong pha bep.

## 4. Roadmap tong quan

### Level 1: "Run duoc cua so"
Tap trung vao setup moi truong, JavaFX run duoc, project compile on dinh.

### Level 2: "Dung chuc nang assignment"
Tap trung vao UI controls, event handling, menu, luot choi, scoreboard, luu file.

### Level 3: "Kien truc dep + report chat luong"
Tap trung vao MVC/MVVM nhe, data binding, test strategy, va tai lieu report.

---

## 5. Level 1 - Setup va khoi dong JavaFX

## 5.1 Muc tieu Level 1

1. Chay duoc JavaFX app tren may cua ban.
2. Co skeleton project de sang Level 2 code nhanh.
3. Khong con loi "module path", "class not found", "javafx runtime missing".

## 5.2 Checklist thuc hien

1. Tao branch rieng:
   - `git checkout -b feature/javafx-rps`
2. Kiem tra tool:
   - `java -version`
   - `javac -version`
   - `mvn -version`
3. Chon build strategy:
   - Khuyen nghi: Maven (it loi JavaFX nhat).
   - Co the giu layout cu, nhung de assignment nhanh thi nen theo Maven layout.
4. Tao `pom.xml` voi:
   - `javafx-controls`
   - `javafx-fxml`
   - `junit` (giu test core)
   - `javafx-maven-plugin`
5. Chuyen source:
   - `src/rps/*.java` -> `src/main/java/rps`
   - test -> `src/test/java/rps`
6. Tao app JavaFX toi thieu:
   - `MainFx extends Application`
   - Hien 1 window + 1 label "RPS JavaFX"
7. Run:
   - `mvn clean test`
   - `mvn javafx:run`

## 5.3 Dinh nghia "Done" cho Level 1

1. App mo duoc cua so JavaFX.
2. Test logic cu pass.
3. Teammate clone repo va run duoc bang 2 lenh Maven.

## 5.4 Loi thuong gap va cach sua nhanh

1. `mvn` khong nhan:
   - Mo terminal moi, kiem tra `PATH`, `JAVA_HOME`.
2. `JavaFX runtime components are missing`:
   - Kiem tra dependency JavaFX trong `pom.xml`.
   - Chay bang `mvn javafx:run` thay vi `java ...`.
3. Loi module (`opens` / `exports`) khi dung FXML:
   - Them `module-info.java` dung namespace.
   - `opens rps.ui to javafx.fxml;`

---

## 6. Level 2 - Hoan thanh full tinh nang assignment

## 6.1 Muc tieu Level 2

1. GUI choi duoc tron ven game.
2. Dat tat ca yeu cau trong de bai.
3. Logic core duoc reuse tu Assignment truoc.

## 6.2 Mapping yeu cau -> thanh phan UI

1. Display round:
   - Label `Round X / N`
2. Human choice:
   - 3 buttons `Rock`, `Paper`, `Scissors`
3. Computer prediction:
   - Label `Predicted human move: ...`
4. Computer choice:
   - Label `Computer chose: ...`
5. Round winner:
   - Label `Result: Human Wins / Computer Wins / Draw`
6. Totals:
   - Labels `Human`, `Computer`, `Ties`
7. Menu:
   - `About`, `Exit`, `Start a new game`
8. Change rounds:
   - `Spinner<Integer>` hoac `TextField` validate
   - Ap dung cho game tiep theo, mac dinh 20

## 6.3 Architecture toi thieu de code nhanh va sach

1. Giu nguyen:
   - `Move`, `Rules`, `StandardRules`, `RoundResult`, `Scoreboard`
2. Tao moi:
   - `rps.ui.MainFx`
   - `rps.ui.GameController`
   - `rps.ui.GameViewState` (neu muon binding dep)
3. Dieu chinh `Game`:
   - Tach loop console thanh API "play one round":
     - dau vao: human move + round number
     - dau ra: object chua prediction/computer move/result/score
4. ML persistence:
   - Dung lai `MachineLearningComputerStrategy`
   - Duong dan file on dinh, vi du `data/rps-ml-data.txt`

## 6.4 Callback events can cai dat

1. Button event:
   - `onRockClicked`
   - `onPaperClicked`
   - `onScissorsClicked`
2. Menu event:
   - `onStartNewGame`
   - `onExit`
   - `onAbout`
3. Window event:
   - `setOnCloseRequest` de goi `onGameOver()` va save data

## 6.5 Thu tu lam viec Level 2 (thuc dung)

1. Tao UI khung (FXML hoac code):
   - Dat het labels + buttons + menu.
2. Noi event cho 3 nut move:
   - Tam thoi chi update 1 label de kiem tra callback.
3. Noi engine:
   - Khi user click, goi logic 1 round.
4. Update UI:
   - prediction, computer move, result, scores, round number.
5. Xu ly end-game:
   - Disable 3 nut move.
   - Hien final summary.
6. Start new game:
   - Reset scoreboard, round, message.
   - Doc so rounds moi tu input control.
7. About dialog:
   - Show assignment info + author.
8. Save ML data:
   - Luc game over va luc app dong.

## 6.6 Test plan Level 2

1. Functional manual test:
   - Choi het 20 rounds -> ket thuc dung.
   - Start new game -> reset dung.
   - Doi rounds truoc game moi -> ap dung dung.
2. Persistence test:
   - Choi vai round, dong app, mo lai.
   - Kiem tra file `data/rps-ml-data.txt` co cap nhat.
3. Regression logic test:
   - Chay lai JUnit core tests.

## 6.7 Dinh nghia "Done" cho Level 2

1. App dat full feature list cua assignment.
2. Khong crash khi user bam lien tuc.
3. ML data file duoc tao va ghi on dinh.

---

## 7. Level 3 - Kien truc dep, de bao tri, de viet report

## 7.1 Muc tieu Level 3

1. Code UI va game logic tach ro.
2. Co data binding de giam update tay.
3. Co tai lieu/report chat luong cho phan "event + callback + IoC".

## 7.2 Nang cap kien truc de len "professional"

1. Tach package:
   - `rps.core` (logic)
   - `rps.ml` (strategy + persistence)
   - `rps.ui` (JavaFX)
2. Gioi han trach nhiem:
   - Controller: nhan event, goi service.
   - Service/Engine: tinh toan game.
   - ViewState: giu JavaFX properties.
3. Data binding:
   - Label text bind truc tiep vao properties.
   - Controller khong can setText nhieu lan.
4. Test:
   - Unit test cho engine va persistence edge cases.

## 7.3 Mau ViewState de xai binding

1. `IntegerProperty roundNumber`
2. `StringProperty predictedHumanMove`
3. `StringProperty computerMove`
4. `StringProperty roundResult`
5. `IntegerProperty humanWins`
6. `IntegerProperty computerWins`
7. `IntegerProperty ties`

## 7.4 Report plan (bao sat de bai)

### A. "What events does your app generate?"
Liet ke event cu the:

1. Button click events.
2. Menu item action events.
3. Spinner/TextField change events.
4. Window close request event.

### B. "How did callback functions handle events?"
Neu bang flow:

1. User click `Rock`.
2. JavaFX dispatch `ActionEvent` -> callback `onRockClicked`.
3. Callback goi game engine -> nhan round result.
4. Callback cap nhat ViewState -> UI tu refresh.

### C. "How reuse code from Assignment #4 with inversion of control?"
Noi ro:

1. Reuse `Rules`, `RoundResult`, `Scoreboard`, `MachineLearningComputerStrategy`.
2. Chi thay input/output layer.
3. IoC: Framework JavaFX goi callback cua minh, khong phai minh polling input nhu console.

### D. Tai lieu hoa de lay diem report

1. 1 so do package architecture.
2. 1 sequence diagram event flow.
3. 2-3 screenshots UI.
4. 3 code snippets ngan:
   - callback method
   - binding
   - call save ML data

## 7.5 Dinh nghia "Done" cho Level 3

1. Code de doc, de explain trong report.
2. Report tra loi du 3 cau hoi assignment.
3. Co artifacts minh hoa (diagram, screenshot, snippet).

---

## 8. De xuat lich thuc hien ngan (3 ngay)

## Ngay 1

1. Hoan thanh Level 1.
2. Tao UI skeleton + callback wiring co ban.

## Ngay 2

1. Hoan thanh Level 2 logic va menu.
2. Hoan thanh persistence + manual test matrix.

## Ngay 3

1. Don code theo Level 3.
2. Viet report + chup screenshot + zip nop bai.

---

## 9. Checklist truoc khi zip nop bai

1. Source compile va run duoc tren may sach.
2. GUI co du tat ca thong tin de bai yeu cau.
3. Menu co du `About`, `Exit`, `Start a new game`.
4. Default 20 rounds va doi duoc rounds cho game tiep theo.
5. ML frequency table duoc save ra file.
6. Report tra loi du event/callback/IoC va co minh hoa.
7. Tao file zip chua source + report.

---

## 10. Next action khuyen nghi ngay bay gio

1. Chot build strategy: Maven.
2. Scaffold Level 1 skeleton trong 30-45 phut.
3. Commit milestone:
   - `feat(level1): setup javafx baseline`
   - `feat(level2): playable gui with menu and persistence`
   - `docs(report): event-callback-ioc explanation`
