import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static JFrame frame;
    private static JTextArea story;
    private static int nextClue = 0;
    static boolean mayaContradictionSolved = false;
    static boolean noraContradictionSolved = false;
    static boolean originalPageRecovered = false;
    static boolean p17Unlocked = false;
    private static final List<String> collectedClues = new ArrayList<>();

    // Case #002 has its own evidence and progress, so Case #001 stays intact.
    private static int nextCase2Clue = 0;
    private static final List<String> collectedCase2Clues = new ArrayList<>();
    private static boolean case2Solved = false;

    private static final String[] CASE2_CLUES = {
        "CLUE 1 — THE BLACKOUT REPORT\n\n"
        + "The museum lost power from 10:12 PM to 10:24 PM.\n"
        + "The display-case sensor did not report an opening during that window.\n\n"
        + "This means the blackout alone does not explain how the object vanished.",

        "CLUE 2 — THE EVENING PHOTOGRAPH\n\n"
        + "A photograph taken at 10:05 PM shows a clock on the display stand.\n"
        + "Compared with the conservation photograph of the original Aster Clock, "
        + "the display clock has a different minute hand and lacks the original's crescent-shaped crack.\n\n"
        + "The object on display may have been a replica before the blackout.",

        "CLUE 3 — THE CONSERVATION TRANSFER\n\n"
        + "The museum's signed transfer record shows that restorer Priya moved the original Aster Clock "
        + "to the conservation workshop three days before the blackout.\n"
        + "The transfer was authorized, but the public inventory was not updated.\n\n"
        + "Priya moved the original clock, but this record does not make her responsible for the staged disappearance.",

        "CLUE 4 — THE SECURITY CONSOLE LOG\n\n"
        + "A manual blackout test was requested shortly before the power interruption. "
        + "The request used a temporary visitor authorization linked to Victor's donor pass.\n"
        + "The security technician's routine test schedule does not include that time.\n\n"
        + "Someone with Victor's pass arranged the blackout, but the pass record alone is not proof of who used it.",

        "CLUE 5 — THE DRAFT MESSAGE\n\n"
        + "A recovered draft on the museum's guest terminal reads: "
        + "'Once the stand is empty, they will report the original missing. The claim will be worth more than the loan.'\n"
        + "The draft was saved under Victor's visitor session shortly before the blackout.\n\n"
        + "Together with the pass record and the replica evidence, this strongly links Victor to staging the disappearance."
    };

    private static final String[] CASE2_SUSPECTS = {
        "Elise — Museum curator",
        "Omar — Security technician",
        "Priya — Clock restorer",
        "Victor — Donor and lender"
    };

    // Case #003 has independent progress so earlier cases remain playable.
    private static int nextCase3Clue = 0;
    private static final List<String> collectedCase3Clues = new ArrayList<>();
    private static boolean case3Solved = false;

    private static final String[] CASE3_CLUES = {
        "CLUE 1 — TWO COPIES OF THE LEDGER\n\n"
        + "Investigator Amara receives two copies of the same payment ledger.\n"
        + "Copy A lists three payments to Northstar Consulting. Copy B lists those same payment IDs as payments to ordinary vendors.\n\n"
        + "The payment IDs match, but the recipient names do not. At least one copy was altered.",

        "CLUE 2 — THE ORIGINAL CHECKSUM\n\n"
        + "The finance server's signed checksum matches Copy A, not Copy B.\n"
        + "The server snapshot was created before the leak and cannot be edited by ordinary staff accounts.\n\n"
        + "Copy A is the best-supported original record. Copy B was changed later.",

        "CLUE 3 — THE AUDIT TERMINAL\n\n"
        + "The change history shows Copy B was exported and edited from the senior audit terminal at 8:41 PM.\n"
        + "The access badge assigned to Rhea, the senior auditor, opened that terminal room at 8:37 PM.\n\n"
        + "This links Rhea to the altered copy, though the badge alone is not absolute proof of who used it.",

        "CLUE 4 — THE THREATENING NOTE\n\n"
        + "Amara receives an anonymous message: 'Stop comparing the ledgers. Publish the wrong name and you will regret it.'\n"
        + "The message was routed through a private relay paid for by Meridian Advisory, a firm that received a consulting fee from Northstar Consulting.\n\n"
        + "The message appears designed to stop the investigation and push Amara toward a false accusation.",

        "CLUE 5 — THE DRAFT INVOICE\n\n"
        + "A recovered draft invoice from Meridian Advisory includes the phrase 'contain the ledger inquiry' and a fee marked 'crisis communications'.\n"
        + "The draft was created under Celeste's broker account. Northstar's payments appear in the original ledger, Copy A.\n\n"
        + "Together with the relay payment, this strongly connects Celeste to the pressure campaign. It does not prove she personally edited the ledger."
    };

    private static final String[] CASE3_SUSPECTS = {
        "Daniel — Finance analyst",
        "Rhea — Senior auditor",
        "Malik — Records clerk",
        "Celeste — Corporate broker"
    };

    private static final Color BG = new Color(18, 22, 33);
    private static final Color PANEL = new Color(29, 35, 50);
    private static final Color TEXT = new Color(238, 240, 245);
    private static final Color MUTED = new Color(170, 180, 197);
    private static final Color ACCENT = new Color(119, 164, 220);

    private static final String[] CLUE_TITLES = {
        "CLUE 1 — THE ACCESS LOG",
        "CLUE 2 — THE BLUE THREAD",
        "CLUE 3 — THE RETURNS CART",
        "CLUE 4 — THE STOPPED CLOCK",
        "CLUE 5 — THE TRANSFER SHEET"
    };

    private static final String[] CLUES = {
        "CLUE 1 — THE ACCESS LOG\n\n"
        + "At 9:12 PM, Maya's staff card opened the archive door.\n"
        + "Leo's departure was recorded at 8:50 PM.\n"
        + "At 9:05 PM, Nora's manager override opened the archive's "
        + "records cabinet, not the main door.\n\n"
        + "WHAT IT PROVES: Maya's card was used to enter the archive.\n"
        + "WHAT IT DOES NOT PROVE: Who moved the manuscript, or why.",

        "CLUE 2 — THE BLUE THREAD\n\n"
        + "A short blue thread was caught on the manuscript cabinet.\n"
        + "It matches a torn section of thread on Maya's blue jacket.\n"
        + "Maya said she never entered the archive that night.\n\n"
        + "The thread challenges her statement, but it cannot tell you "
        + "whether she stole the manuscript, hid it, or touched the cabinet "
        + "for another reason.",

        "CLUE 3 — THE RETURNS CART\n\n"
        + "The missing manuscript was found in a locked compartment "
        + "of the returns cart assigned to Maya.\n"
        + "The checkout register shows Maya signed out that cart at 9:24 PM. "
        + "No one else signed for it afterward.\n\n"
        + "Maya had the manuscript in her possession after it vanished. "
        + "That is strong evidence that she moved it—but possession alone "
        + "does not explain her motive.",

        "CLUE 4 — THE CLOCK MAINTENANCE LOG\n\n"
        + "The archive clock stopped at 9:15 PM. Nora's maintenance log "
        + "shows she stopped it at 8:30 PM for servicing, then forgot to "
        + "restart it.\n\n"
        + "The clock was not recording the time of the disappearance. "
        + "Any alibi based only on the clock is unreliable.\n\n"
        + "A useful clue can be genuine and still lead to a wrong conclusion.",

        "CLUE 5 — THE TRANSFER SHEET\n\n"
        + "A thin transfer sheet was found inside Nora's locked records folder. "
        + "It contains pressure marks matching several unusual phrases on "
        + "the manuscript's replacement final page.\n"
        + "The sheet is dated two days before the manuscript disappeared, "
        + "and the records-folder checkout bears Nora's signature.\n\n"
        + "This links Nora to preparation of the replacement page. It does "
        + "not prove she moved the manuscript.\n\n"
        + "At the bottom is a small reference: P-17."
    };

    private static final String[] SUSPECTS = {
        "Alex — Night guard",
        "Maya — Research assistant",
        "Leo — Visiting collector",
        "Nora — Archive manager"
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::showMenu);
    }

    private static void showMenu() {
        frame = new JFrame("MYSTERY ENGINE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 650);
        frame.setMinimumSize(new Dimension(760, 560));
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(BG);
        panel.setBorder(new EmptyBorder(35, 35, 35, 35));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel eyebrow = new JLabel("THE VEIL FILES");
        eyebrow.setFont(new Font("SansSerif", Font.BOLD, 14));
        eyebrow.setForeground(ACCENT);
        eyebrow.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("MYSTERY ENGINE");
        title.setFont(new Font("Serif", Font.BOLD, 48));
        title.setForeground(TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Every clue hides a truth. Every truth leaves a trace.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setForeground(MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea intro = new JTextArea(
            "CASE FILE 001  /  THE MIDNIGHT ARCHIVE\n\n"
            + "A rare manuscript has vanished from a locked university archive. "
            + "One person moved it. Someone else may have changed what it said.\n\n"
            + "Investigate the evidence, question four suspects, and separate "
            + "what the clues prove from what they merely suggest."
        );
        intro.setEditable(false);
        intro.setLineWrap(true);
        intro.setWrapStyleWord(true);
        intro.setFont(new Font("SansSerif", Font.PLAIN, 16));
        intro.setForeground(TEXT);
        intro.setBackground(PANEL);
        intro.setBorder(new EmptyBorder(18, 20, 18, 20));
        intro.setMaximumSize(new Dimension(650, 170));

        JButton start = makeButton("START INVESTIGATION");
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.addActionListener(e -> showCase());

        panel.add(Box.createVerticalGlue());
        panel.add(eyebrow);
        panel.add(Box.createVerticalStrut(10));
        panel.add(title);
        panel.add(Box.createVerticalStrut(12));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(35));
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(intro);
        panel.add(Box.createVerticalStrut(28));
        panel.add(start);
        panel.add(Box.createVerticalGlue());

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private static void showCase() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(BG);
        panel.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel heading = new JLabel("CASE #001: THE MIDNIGHT ARCHIVE");
        heading.setForeground(TEXT);
        heading.setFont(new Font("Serif", Font.BOLD, 25));

        story = new JTextArea(caseIntro());
        styleStory(story);

        JPanel buttons = new JPanel(new GridLayout(0, 3, 10, 10));
        buttons.setBackground(BG);

        JButton clueButton = makeButton("Inspect Evidence");
        JButton suspectButton = makeButton("Question Suspects");
        JButton journalButton = makeButton("Evidence Journal");
        JButton deductionButton = makeButton("Deduction Board");
        JButton accuseButton = makeButton("Make an Accusation");
        JButton restartButton = makeButton("Restart Case");
        JButton contradictionButton = makeButton("Contradiction Board");
        
        JButton originalPageButton = makeButton("Recover Original Page");

        originalPageButton.addActionListener(
                e -> recoverOriginalPage()
        );
        
        JButton p17Button = makeButton("Open P-17 File");

        p17Button.addActionListener(e -> openP17File());



contradictionButton.addActionListener(e -> showContradictionBoard());

        clueButton.addActionListener(e -> inspectClue());
        suspectButton.addActionListener(e -> questionSuspect());
        journalButton.addActionListener(e -> showJournal());
        deductionButton.addActionListener(e -> showDeduction());
        accuseButton.addActionListener(e -> accuse());
        restartButton.addActionListener(e -> restartCase());

        buttons.add(clueButton);
        buttons.add(suspectButton);
        buttons.add(journalButton);
        buttons.add(deductionButton);
        buttons.add(accuseButton);
        buttons.add(restartButton);
        buttons.add(contradictionButton);
        buttons.add(originalPageButton);
        buttons.add(p17Button);

        JLabel status = new JLabel("  Evidence collected: 0 / " + CLUES.length + "     |     Case status: OPEN");
        status.setForeground(MUTED);
        status.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel center = new JPanel(new BorderLayout(0, 8));
        center.setBackground(BG);
        center.add(status, BorderLayout.NORTH);
        center.add(new JScrollPane(story), BorderLayout.CENTER);

        panel.add(heading, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private static String caseIntro() {
        return "CASE #001: THE MIDNIGHT ARCHIVE\n\n"
            + "A rare manuscript has vanished from a locked university archive.\n"
            + "Four people had a reason to care about it: Alex, the night guard; "
            + "Maya, the research assistant; Leo, a visiting collector; and Nora, "
            + "the archive manager.\n\n"
            + "The archive clock stopped at 9:15 PM, but its maintenance history "
            + "may make that time unreliable.\n\n"
            + "YOUR TASK\n"
            + "Find out who moved the manuscript—and who prepared a replacement "
            + "final page. These may not be the same person.\n\n"
            + "Start with the evidence. Do not confuse access, possession, and motive.";
    }

    private static void styleStory(JTextArea area) {
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("SansSerif", Font.PLAIN, 16));
        area.setBackground(PANEL);
        area.setForeground(TEXT);
        area.setCaretColor(TEXT);
        area.setMargin(new Insets(18, 18, 18, 18));
    }

    private static JButton makeButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBackground(new Color(48, 59, 80));
        button.setForeground(TEXT);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 91, 118)),
            new EmptyBorder(10, 8, 10, 8)
        ));
        return button;
    }

    private static void inspectClue() {
        if (nextClue >= CLUES.length) {
            story.setText("YOU HAVE INSPECTED EVERY AVAILABLE CLUE\n\n"
                + "Open the Evidence Journal to review everything you found.\n"
                + "Then use the Deduction Board to separate the two questions:\n"
                + "1. Who moved the manuscript?\n"
                + "2. Who prepared the replacement page?");
            return;
        }

        collectedClues.add(CLUES[nextClue]);
        story.setText(CLUES[nextClue]);
        nextClue++;
    }

    private static void questionSuspect() {
        String choice = (String) JOptionPane.showInputDialog(
            frame, "Who do you want to question?", "Suspect Interviews",
            JOptionPane.QUESTION_MESSAGE, null, SUSPECTS, SUSPECTS[0]
        );
        if (choice == null) return;

        if (choice.startsWith("Alex")) {
            story.setText("ALEX — NIGHT GUARD\n\n"
                + "\"I was at the front entrance from 9:00 to 9:30 PM. "
                + "I heard a cart wheel squeak in the corridor, but I did not "
                + "see who was pushing it. Check the guard log.\"\n\n"
                + "OBSERVATION: Alex's account could fit the cart being moved, "
                + "but he cannot identify who moved it.");
        } else if (choice.startsWith("Maya")) {
            story.setText("MAYA — RESEARCH ASSISTANT\n\n"
                + "\"I stayed in the reading room. I never entered the archive, "
                + "and I did not take the manuscript.\"\n\n"
                + "OBSERVATION: Her statement conflicts with the access log and "
                + "the blue thread. She is concealing something, but a lie alone "
                + "does not establish the full reason.");
        } else if (choice.startsWith("Leo")) {
            story.setText("LEO — VISITING COLLECTOR\n\n"
                + "\"I left at 8:50 PM. I wanted to purchase the manuscript, "
                + "but the university refused. I never entered the archive "
                + "after leaving.\"\n\n"
                + "OBSERVATION: Leo had a possible motive to obtain the manuscript, "
                + "but the departure record puts him outside before the logged entry.");
        } else {
            story.setText("NORA — ARCHIVE MANAGER\n\n"
                + "\"The clock stopped during scheduled maintenance at 8:30 PM. "
                + "I logged it myself. The manuscript's final page was already "
                + "under review, and I had access to the records folder.\"\n\n"
                + "OBSERVATION: Nora admits access to the records folder. Check "
                + "the transfer sheet before deciding what that means.");
        }
    }

    private static void showJournal() {
        if (collectedClues.isEmpty()) {
            story.setText("EVIDENCE JOURNAL\n\nNo evidence collected yet.\n"
                + "Inspect the evidence to begin.");
            return;
        }

        StringBuilder journal = new StringBuilder("EVIDENCE JOURNAL\n\n");
        for (String clue : collectedClues) {
            journal.append(clue).append("\n\n")
                   .append("--------------------------------------------------\n\n");
        }
        journal.append("REMINDER\nA clue may prove one fact without proving the whole theory.");
        story.setText(journal.toString());
    }

    private static void showDeduction() {
        if (nextClue < CLUES.length) {
            story.setText("DEDUCTION BOARD\n\n"
                + "You have collected " + nextClue + " of " + CLUES.length + " clues.\n\n"
                + "Inspect the remaining evidence first. The case asks two different "
                + "questions: who moved the manuscript, and who prepared the "
                + "replacement page. Do not assume the answers are the same.");
            return;
        }

        story.setText("DEDUCTION BOARD\n\n"
            + "QUESTION A — WHO MOVED THE MANUSCRIPT?\n"
            + "• Maya's card opened the archive at 9:12 PM.\n"
            + "• The blue thread connects her jacket to the cabinet.\n"
            + "• The manuscript was later found in the cart she checked out.\n"
            + "• Her interview denies entering the archive.\n\n"
            + "These clues strongly support that Maya moved the manuscript. "
            + "They do not, by themselves, establish why.\n\n"
            + "QUESTION B — WHO PREPARED THE REPLACEMENT PAGE?\n"
            + "• Nora's signed records folder contained a transfer sheet.\n"
            + "• Pressure marks on it match unusual phrases on the replacement page.\n"
            + "• The sheet predates the disappearance.\n\n"
            + "These clues point to Nora preparing the replacement page.\n\n"
            + "THE TWIST\n"
            + "Maya moved the manuscript after discovering the page had been replaced. "
            + "She lied to conceal her own unauthorized action. Nora's suspected "
            + "forgery is a separate act. One person can be guilty of hiding evidence "
            + "without being responsible for creating the forgery.\n\n"
            + "A small reference on the transfer sheet reads P-17. Its meaning is "
            + "not yet established.");
    }

    private static void accuse() {
        if (nextClue < CLUES.length) {
            int result = JOptionPane.showConfirmDialog(
                frame,
                "You have inspected " + nextClue + " of " + CLUES.length + " clues.\n"
                    + "Make an accusation without all the evidence?",
                "Evidence Incomplete", JOptionPane.YES_NO_OPTION
            );
            if (result != JOptionPane.YES_OPTION) {
                story.setText("INVESTIGATION PAUSED\n\n"
                    + "There are still clues to inspect. A good detective distinguishes "
                    + "suspicion from evidence.");
                return;
            }
        }

        String[] options = {
            "Alex — Night guard", "Maya — Research assistant",
            "Leo — Visiting collector", "Nora — Archive manager"
        };

        String mover = (String) JOptionPane.showInputDialog(
            frame, "First: who moved the manuscript?", "Final Deduction — Part 1",
            JOptionPane.QUESTION_MESSAGE, null, options, options[0]
        );
        if (mover == null) return;

        String forger = (String) JOptionPane.showInputDialog(
            frame, "Second: who prepared the replacement final page?",
            "Final Deduction — Part 2", JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]
        );
        if (forger == null) return;

        boolean correctMover = mover.startsWith("Maya");
        boolean correctForger = forger.startsWith("Nora");
        int score = (nextClue * 10) + (correctMover ? 25 : 0) + (correctForger ? 25 : 0);
        score = Math.min(score, 100);

        StringBuilder ending = new StringBuilder("FINAL CASE REPORT\n\n");
        ending.append("Your conclusion:\n")
              .append("• Manuscript moved by: ").append(mover).append("\n")
              .append("• Replacement page prepared by: ").append(forger).append("\n\n");

        if (correctMover && correctForger) {
            ending.append("CASE SOLVED — BUT NOT THE WAY IT FIRST APPEARED.\n\n")
                .append("Maya moved the manuscript into the returns cart after discovering "
                    + "that its final page had been replaced. She lied because she feared "
                    + "being blamed for moving it. The access log, thread, cart register, "
                    + "and her contradiction expose her part in the concealment.\n\n")
                .append("Nora prepared the replacement page. The dated transfer sheet in "
                    + "her signed records folder connects her to the unusual wording. "
                    + "Maya's actions and Nora's forgery are separate wrongs.\n\n")
                .append("The transfer sheet carries one unexplained mark: P-17.\n")
                .append("CASE #002 remains sealed.");
        } else {
            ending.append("THE CASE IS ONLY PARTLY SOLVED.\n\n");
            if (correctMover) {
                ending.append("You correctly identified who moved the manuscript, but "
                    + "the evidence about the replacement page points elsewhere.\n\n");
            } else {
                ending.append("The manuscript evidence points to Maya as the person who "
                    + "moved it. The clock was stopped for maintenance, and the access "
                    + "record alone was never enough to establish the full story.\n\n");
            }
            if (correctForger) {
                ending.append("You correctly connected Nora to preparation of the "
                    + "replacement page.\n\n");
            } else {
                ending.append("The dated transfer sheet in Nora's signed records folder "
                    + "is the strongest evidence about who prepared the replacement page.\n\n");
            }
            ending.append("Review the Evidence Journal and Deduction Board, then restart "
                + "the case to try a different conclusion.");
        }

        ending.append("\n\nEvidence inspected: ").append(nextClue).append("/")
              .append(CLUES.length)
              .append("\nDetective score: ").append(score).append("/100");
        story.setText(ending.toString());
    }

    private static void restartCase() {
        int choice = JOptionPane.showConfirmDialog(
            frame, "Restart the Midnight Archive investigation?",
            "Restart Case", JOptionPane.YES_NO_OPTION
        );
        if (choice == JOptionPane.YES_OPTION) {
            nextClue = 0;
            collectedClues.clear();
            mayaContradictionSolved = false;
            noraContradictionSolved = false;
            originalPageRecovered = false;
            p17Unlocked = false;
            story.setText(caseIntro());
        }
    }
    
static void showContradictionBoard() {

    String[] options = {
        "Compare Maya's Statement",
        "Compare Nora's Statement",
        "Return"
    };

    int choice = JOptionPane.showOptionDialog(
        frame,
        "Which statement do you want to investigate?",
        "CONTRADICTION BOARD",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]
    );

    if (choice == 0) {

        if (nextClue >= 2) {
            mayaContradictionSolved = true;

            JOptionPane.showMessageDialog(
                frame,
                "CONTRADICTION FOUND!\n\n"
                + "Maya claimed she never entered the archive.\n"
                + "But the access log records her staff card, "
                + "and the blue thread connects her jacket to the cabinet.\n\n"
                + "Conclusion: Maya's statement is unreliable.\n"
                + "This does NOT prove she prepared the replacement page.",
                "Maya's Contradiction",
                JOptionPane.WARNING_MESSAGE
            );

        } else {
            JOptionPane.showMessageDialog(
                frame,
                "You need to inspect at least two clues first!",
                "Not Enough Evidence",
                JOptionPane.INFORMATION_MESSAGE
            );
        }

    } else if (choice == 1) {

        if (nextClue >= 5) {
            noraContradictionSolved = true;

            JOptionPane.showMessageDialog(
                frame,
                "A PATTERN EMERGES!\n\n"
                + "The clock record and transfer documents "
                + "raise questions about Nora's timeline.\n"
                + "The transfer sheet also links her to the "
                + "replacement-page investigation.\n\n"
                + "Conclusion: Nora's explanation needs "
                + "further investigation.",
                "Nora's Contradiction",
                JOptionPane.WARNING_MESSAGE
            );

        } else {
            JOptionPane.showMessageDialog(
                frame,
                "You need to inspect all five clues first!",
                "Not Enough Evidence",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}

static void recoverOriginalPage() {

    if (mayaContradictionSolved && noraContradictionSolved) {

        originalPageRecovered = true;
        p17Unlocked = true;

        JOptionPane.showMessageDialog(
            frame,
            "ORIGINAL FINAL PAGE RECOVERED\n\n"
            + "\"The record was not destroyed. "
            + "It was replaced before the archive was sealed.\"\n\n"
            + "A handwritten note appears beneath the text:\n"
            + "\"Transfer reference: P-17. "
            + "The copy is only one part of the record.\"\n\n"
            + "NEW LEAD UNLOCKED: P-17 FILE",
            "The Missing Page",
            JOptionPane.INFORMATION_MESSAGE
        );

    } else {

        JOptionPane.showMessageDialog(
            frame,
            "The original page is still out of reach.\n\n"
            + "Solve both Maya's and Nora's contradictions "
            + "on the Contradiction Board first.",
            "Evidence Incomplete",
            JOptionPane.WARNING_MESSAGE
        );
    }
}

static void openP17File() {

    if (!p17Unlocked) {
        JOptionPane.showMessageDialog(
            frame,
            "ACCESS DENIED\n\nYou must recover the original final page first.",
            "Restricted File",
            JOptionPane.INFORMATION_MESSAGE
        );
        return;
    }

    showP17Screen();
}

private static void showP17Screen() {
    JPanel panel = new JPanel(new BorderLayout(14, 14));
    panel.setBackground(BG);
    panel.setBorder(new EmptyBorder(24, 24, 24, 24));

    JLabel heading = new JLabel("THE VEIL FILES  /  RESTRICTED RECORD P-17");
    heading.setForeground(ACCENT);
    heading.setFont(new Font("Serif", Font.BOLD, 26));

    JTextArea fileText = new JTextArea(
        "FILE: P-17\nSTATUS: PARTIAL\n\n"
        + "The replacement page was prepared before the manuscript disappeared.\n\n"
        + "P-17 appears in transfer records from more than one institution. "
        + "Someone may be altering records to change how evidence is interpreted.\n\n"
        + "This file does not identify who created the system. It gives you the next lead.\n\n"
        + "NEXT INVESTIGATION: CASE #002 — THE THIRTEENTH HOUR\n\n"
        + "A museum clock reportedly vanished during a blackout. The timeline may be a lie."
    );
    styleStory(fileText);
    fileText.setMargin(new Insets(20, 20, 20, 20));

    JPanel buttons = new JPanel(new GridLayout(1, 3, 12, 12));
    buttons.setBackground(BG);
    JButton case2Button = makeButton("OPEN CASE #002 — THE THIRTEENTH HOUR");
    JButton case3Button = makeButton("OPEN CASE #003 — THE SILENT WITNESS");
    JButton backButton = makeButton("RETURN TO CASE #001");
    case2Button.addActionListener(e -> showCase2());
    case3Button.addActionListener(e -> showCase3());
    backButton.addActionListener(e -> showCase());
    buttons.add(case2Button);
    buttons.add(case3Button);
    buttons.add(backButton);

    panel.add(heading, BorderLayout.NORTH);
    panel.add(new JScrollPane(fileText), BorderLayout.CENTER);
    panel.add(buttons, BorderLayout.SOUTH);
    frame.setContentPane(panel);
    frame.revalidate();
    frame.repaint();
}

private static void showCase2() {
    if (!p17Unlocked) {
        JOptionPane.showMessageDialog(frame, "Case #002 is locked. Open the P-17 file first.",
            "Case Locked", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    JPanel panel = new JPanel(new BorderLayout(12, 12));
    panel.setBackground(BG);
    panel.setBorder(new EmptyBorder(18, 18, 18, 18));

    JLabel heading = new JLabel("CASE #002: THE THIRTEENTH HOUR");
    heading.setForeground(TEXT);
    heading.setFont(new Font("Serif", Font.BOLD, 25));

    story = new JTextArea(case2Intro());
    styleStory(story);

    JPanel buttons = new JPanel(new GridLayout(0, 3, 10, 10));
    buttons.setBackground(BG);
    JButton inspect = makeButton("Inspect Evidence");
    JButton interview = makeButton("Question Suspects");
    JButton journal = makeButton("Evidence Journal");
    JButton deduction = makeButton("Deduction Board");
    JButton accuse = makeButton("Make an Accusation");
    JButton restart = makeButton("Restart Case #002");
    JButton p17 = makeButton("P-17 File");
    JButton case3 = makeButton("Case #003 — The Silent Witness");
    JButton back = makeButton("Back to Case #001");

    inspect.addActionListener(e -> inspectCase2Clue());
    interview.addActionListener(e -> questionCase2Suspect());
    journal.addActionListener(e -> showCase2Journal());
    deduction.addActionListener(e -> showCase2Deduction());
    accuse.addActionListener(e -> accuseCase2());
    restart.addActionListener(e -> restartCase2());
    p17.addActionListener(e -> showP17Screen());
    case3.addActionListener(e -> showCase3());
    back.addActionListener(e -> showCase());

    buttons.add(inspect);
    buttons.add(interview);
    buttons.add(journal);
    buttons.add(deduction);
    buttons.add(accuse);
    buttons.add(restart);
    buttons.add(p17);
    buttons.add(case3);
    buttons.add(back);

    JLabel status = new JLabel("  Evidence collected: " + nextCase2Clue + " / " + CASE2_CLUES.length
        + "     |     Case status: " + (case2Solved ? "SOLVED" : "OPEN"));
    status.setForeground(MUTED);
    status.setFont(new Font("SansSerif", Font.PLAIN, 12));

    JPanel center = new JPanel(new BorderLayout(0, 8));
    center.setBackground(BG);
    center.add(status, BorderLayout.NORTH);
    center.add(new JScrollPane(story), BorderLayout.CENTER);

    panel.add(heading, BorderLayout.NORTH);
    panel.add(center, BorderLayout.CENTER);
    panel.add(buttons, BorderLayout.SOUTH);
    frame.setContentPane(panel);
    frame.revalidate();
    frame.repaint();
}

private static String case2Intro() {
    return "CASE #002: THE THIRTEENTH HOUR\n\n"
        + "The Aster Clock, a rare antique, has reportedly vanished from a museum display during a blackout.\n\n"
        + "Four people are connected to it: Elise, the curator; Omar, the security technician; "
        + "Priya, the restorer; and Victor, the donor who loaned the clock.\n\n"
        + "Your task: determine what actually disappeared, who arranged the blackout, and why the inventory tells a different story.\n\n"
        + "Detective rule: access and motive are leads—not proof. Collect all five clues before accusing anyone.";
}

private static void inspectCase2Clue() {
    if (nextCase2Clue >= CASE2_CLUES.length) {
        story.setText("YOU HAVE INSPECTED ALL FIVE CLUES.\n\nReview the Evidence Journal, then use the Deduction Board before making your accusation.");
        return;
    }
    collectedCase2Clues.add(CASE2_CLUES[nextCase2Clue]);
    story.setText(CASE2_CLUES[nextCase2Clue]);
    nextCase2Clue++;
}

private static void questionCase2Suspect() {
    String choice = (String) JOptionPane.showInputDialog(frame,
        "Who do you want to question?", "Case #002 — Interviews",
        JOptionPane.QUESTION_MESSAGE, null, CASE2_SUSPECTS, CASE2_SUSPECTS[0]);
    if (choice == null) return;

    if (choice.startsWith("Elise")) {
        story.setText("ELISE — MUSEUM CURATOR\n\n\"The Aster Clock was on display when I left. The inventory still listed it there.\"\n\n"
            + "OBSERVATION: The inventory may be outdated. Compare it with the signed conservation transfer.");
    } else if (choice.startsWith("Omar")) {
        story.setText("OMAR — SECURITY TECHNICIAN\n\n\"The blackout was unusual. I did not schedule a test for that time.\"\n\n"
            + "OBSERVATION: The console log records a temporary visitor authorization, not Omar's normal test schedule.");
    } else if (choice.startsWith("Priya")) {
        story.setText("PRIYA — CLOCK RESTORER\n\n\"I moved the original Aster Clock to the conservation workshop under a signed transfer order three days earlier.\"\n\n"
            + "OBSERVATION: Her statement matches the transfer record. Moving the original does not prove she staged the later incident.");
    } else {
        story.setText("VICTOR — DONOR AND LENDER\n\n\"I only wanted the museum to protect my family's clock. I was nowhere near the security console.\"\n\n"
            + "OBSERVATION: His visitor pass is linked to a blackout request. Check the terminal draft before deciding what that means.");
    }
}

private static void showCase2Journal() {
    if (collectedCase2Clues.isEmpty()) {
        story.setText("CASE #002 — EVIDENCE JOURNAL\n\nNo evidence collected yet. Click Inspect Evidence to begin.");
        return;
    }
    StringBuilder journal = new StringBuilder("CASE #002 — EVIDENCE JOURNAL\n\n");
    for (String clue : collectedCase2Clues) {
        journal.append(clue).append("\n\n----------------------------------------\n\n");
    }
    journal.append("REMINDER\nA clue can establish one fact without proving the whole theory.");
    story.setText(journal.toString());
}

private static void showCase2Deduction() {
    if (nextCase2Clue < CASE2_CLUES.length) {
        story.setText("DEDUCTION BOARD\n\nYou have inspected " + nextCase2Clue + " of " + CASE2_CLUES.length
            + " clues. Find the remaining evidence before making a final deduction.");
        return;
    }
    story.setText("DEDUCTION BOARD — THE THIRTEENTH HOUR\n\n"
        + "QUESTION A — WHO MOVED THE ORIGINAL ASTER CLOCK?\n"
        + "The signed conservation transfer shows Priya moved the original to the workshop three days before the blackout.\n\n"
        + "QUESTION B — WHO STAGED THE APPARENT THEFT?\n"
        + "The display photograph suggests the object on the stand was a replica. The visitor pass linked to Victor requested the blackout, and a draft saved in Victor's visitor session refers to making the original appear stolen for a claim.\n\n"
        + "THE TWIST\nThe original clock was not stolen during the blackout: it was already in conservation. The object removed from the display was a replica. Victor used the blackout to make the museum report the original missing. The stale inventory made the lie believable.\n\n"
        + "P-17 appears on a seal number in the conservation transfer index. Someone has been tracking the movement of records and objects across institutions.");
}

private static void accuseCase2() {
    if (nextCase2Clue < CASE2_CLUES.length) {
        int result = JOptionPane.showConfirmDialog(frame,
            "You have inspected " + nextCase2Clue + " of " + CASE2_CLUES.length + " clues. Accuse someone anyway?",
            "Evidence Incomplete", JOptionPane.YES_NO_OPTION);
        if (result != JOptionPane.YES_OPTION) return;
    }

    String mover = (String) JOptionPane.showInputDialog(frame,
        "First: who moved the original Aster Clock to conservation?", "Final Deduction — Part 1",
        JOptionPane.QUESTION_MESSAGE, null, CASE2_SUSPECTS, CASE2_SUSPECTS[0]);
    if (mover == null) return;
    String stager = (String) JOptionPane.showInputDialog(frame,
        "Second: who staged the apparent theft during the blackout?", "Final Deduction — Part 2",
        JOptionPane.QUESTION_MESSAGE, null, CASE2_SUSPECTS, CASE2_SUSPECTS[0]);
    if (stager == null) return;

    boolean correctMover = mover.startsWith("Priya");
    boolean correctStager = stager.startsWith("Victor");
    int score = Math.min(100, nextCase2Clue * 10 + (correctMover ? 25 : 0) + (correctStager ? 25 : 0));
    StringBuilder result = new StringBuilder("CASE #002 — FINAL REPORT\n\n");
    result.append("Original clock moved by: ").append(mover).append("\n")
          .append("Apparent theft staged by: ").append(stager).append("\n\n");

    if (correctMover && correctStager) {
        case2Solved = true;
        result.append("CASE SOLVED — THE THIRTEENTH HOUR\n\n")
            .append("Priya moved the original clock under a signed conservation order. Victor arranged the blackout and removed the replica so the museum would report the original missing. The inventory was never updated, making the deception possible.\n\n")
            .append("NEW LEAD: The conservation transfer index carries the same P-17 reference found in the archive case. The connection is real, but it does not yet prove who is coordinating the record changes.");
    } else {
        result.append("CASE PARTLY SOLVED\n\n")
            .append(correctMover ? "You correctly identified Priya's authorized transfer.\n" : "The signed conservation transfer points to Priya as the person who moved the original.\n")
            .append(correctStager ? "You correctly connected Victor to the staged incident.\n" : "The blackout request and saved draft point to Victor as the person who staged the apparent theft.\n")
            .append("Review the Evidence Journal and Deduction Board, then try again.");
    }
    result.append("\n\nEvidence inspected: ").append(nextCase2Clue).append("/").append(CASE2_CLUES.length)
          .append("\nDetective score: ").append(score).append("/100");
    story.setText(result.toString());
}

private static void showCase3() {
    if (!case2Solved) {
        JOptionPane.showMessageDialog(frame,
            "CASE #003 IS LOCKED\n\nSolve Case #002 — The Thirteenth Hour first. The P-17 lead needs to be verified before this file opens.",
            "Investigation Locked", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    JPanel panel = new JPanel(new BorderLayout(12, 12));
    panel.setBackground(BG);
    panel.setBorder(new EmptyBorder(18, 18, 18, 18));
    JLabel heading = new JLabel("CASE #003: THE SILENT WITNESS");
    heading.setForeground(TEXT);
    heading.setFont(new Font("Serif", Font.BOLD, 25));

    story = new JTextArea(case3Intro());
    styleStory(story);
    JPanel buttons = new JPanel(new GridLayout(0, 3, 10, 10));
    buttons.setBackground(BG);
    JButton inspect = makeButton("Inspect Evidence");
    JButton interview = makeButton("Question Suspects");
    JButton journal = makeButton("Evidence Journal");
    JButton deduction = makeButton("Deduction Board");
    JButton accuse = makeButton("Make an Accusation");
    JButton restart = makeButton("Restart Case #003");
    JButton p17 = makeButton("P-17 File");
    JButton back = makeButton("Back to Case #002");
    inspect.addActionListener(e -> inspectCase3Clue());
    interview.addActionListener(e -> questionCase3Suspect());
    journal.addActionListener(e -> showCase3Journal());
    deduction.addActionListener(e -> showCase3Deduction());
    accuse.addActionListener(e -> accuseCase3());
    restart.addActionListener(e -> restartCase3());
    p17.addActionListener(e -> showP17Screen());
    back.addActionListener(e -> showCase2());
    buttons.add(inspect); buttons.add(interview); buttons.add(journal);
    buttons.add(deduction); buttons.add(accuse); buttons.add(restart);
    buttons.add(p17); buttons.add(back);

    JLabel status = new JLabel("  Evidence collected: " + nextCase3Clue + " / " + CASE3_CLUES.length
        + "     |     Case status: " + (case3Solved ? "SOLVED" : "OPEN"));
    status.setForeground(MUTED);
    status.setFont(new Font("SansSerif", Font.PLAIN, 12));
    JPanel center = new JPanel(new BorderLayout(0, 8));
    center.setBackground(BG);
    center.add(status, BorderLayout.NORTH);
    center.add(new JScrollPane(story), BorderLayout.CENTER);
    panel.add(heading, BorderLayout.NORTH);
    panel.add(center, BorderLayout.CENTER);
    panel.add(buttons, BorderLayout.SOUTH);
    frame.setContentPane(panel);
    frame.revalidate();
    frame.repaint();
}

private static String case3Intro() {
    return "CASE #003: THE SILENT WITNESS\n\n"
        + "Financial investigator Amara is threatened after confidential payment records leak. Two copies of the same ledger disagree—and the altered copy appears to blame Daniel, a finance analyst.\n\n"
        + "Four people are connected to the records: Daniel, the analyst; Rhea, the senior auditor; Malik, the records clerk; and Celeste, a corporate broker.\n\n"
        + "YOUR TASK\nDetermine who altered the ledger and who arranged the pressure campaign against Amara. These may be different people.\n\n"
        + "Collect all five clues, question the suspects, and distinguish the original evidence from the version someone wants you to believe.";
}

private static void inspectCase3Clue() {
    if (nextCase3Clue >= CASE3_CLUES.length) {
        story.setText("ALL FIVE CLUES COLLECTED\n\nOpen the Evidence Journal, question the suspects, then use the Deduction Board before making your accusation.");
        return;
    }
    collectedCase3Clues.add(CASE3_CLUES[nextCase3Clue]);
    story.setText(CASE3_CLUES[nextCase3Clue]);
    nextCase3Clue++;
}

private static void questionCase3Suspect() {
    String choice = (String) JOptionPane.showInputDialog(frame, "Who do you want to question?",
        "Case #003 — Interviews", JOptionPane.QUESTION_MESSAGE, null, CASE3_SUSPECTS, CASE3_SUSPECTS[0]);
    if (choice == null) return;
    if (choice.startsWith("Daniel")) {
        story.setText("DANIEL — FINANCE ANALYST\n\n\"I prepared the monthly payment summary, but I never changed the recipient names. My copy came from the finance server.\"\n\nOBSERVATION: His claim can be checked against the signed server checksum.");
    } else if (choice.startsWith("Rhea")) {
        story.setText("RHEA — SENIOR AUDITOR\n\n\"I reviewed the payments, but I only corrected formatting. I never changed a payee.\"\n\nOBSERVATION: The edit history places a changed export on the audit terminal after the original server snapshot.");
    } else if (choice.startsWith("Malik")) {
        story.setText("MALIK — RECORDS CLERK\n\n\"I gave Amara a copy because the numbers did not match. I wanted the inquiry to be checked, not buried.\"\n\nOBSERVATION: Sharing the discrepancy may explain the leak, but it does not show who altered the ledger.");
    } else {
        story.setText("CELESTE — CORPORATE BROKER\n\n\"I advise companies during disputes. I have no reason to threaten an investigator.\"\n\nOBSERVATION: Meridian Advisory paid for the private relay, and the recovered crisis-communications draft was saved under Celeste's account.");
    }
}

private static void showCase3Journal() {
    if (collectedCase3Clues.isEmpty()) {
        story.setText("CASE #003 — EVIDENCE JOURNAL\n\nNo evidence collected yet. Click Inspect Evidence to begin.");
        return;
    }
    StringBuilder journal = new StringBuilder("CASE #003 — EVIDENCE JOURNAL\n\n");
    for (String clue : collectedCase3Clues) journal.append(clue).append("\n\n----------------------------------------\n\n");
    journal.append("REMINDER\nA clue can support one conclusion without proving every part of the case.");
    story.setText(journal.toString());
}

private static void showCase3Deduction() {
    if (nextCase3Clue < CASE3_CLUES.length) {
        story.setText("DEDUCTION BOARD\n\nYou have inspected " + nextCase3Clue + " of " + CASE3_CLUES.length + " clues. Collect the remaining evidence first.");
        return;
    }
    story.setText("DEDUCTION BOARD — THE SILENT WITNESS\n\n"
        + "QUESTION A — WHO ALTERED THE LEDGER?\n"
        + "The signed server checksum supports Copy A as the original. Copy B was edited later from the senior audit terminal, and Rhea's badge opened that room shortly before the edit. This evidence points to Rhea.\n\n"
        + "QUESTION B — WHO ARRANGED THE PRESSURE CAMPAIGN?\n"
        + "The anonymous threat was routed through a relay paid for by Meridian Advisory. A crisis-communications draft about containing the inquiry was saved under Celeste's broker account. This points to Celeste.\n\n"
        + "THE TWIST\nMalik did pass the discrepancy to Amara, but the leak was not proof that he altered the ledger. Daniel was being framed by the changed copy. Rhea's ledger edit and Celeste's pressure campaign are separate acts.\n\n"
        + "P-17 appears in the audit export metadata too. The same reference keeps surfacing wherever records are manipulated.");
}

private static void accuseCase3() {
    if (nextCase3Clue < CASE3_CLUES.length) {
        int choice = JOptionPane.showConfirmDialog(frame,
            "You have inspected " + nextCase3Clue + " of " + CASE3_CLUES.length + " clues. Accuse someone anyway?",
            "Evidence Incomplete", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) return;
    }
    String editor = (String) JOptionPane.showInputDialog(frame, "First: who altered the ledger copy?",
        "Final Deduction — Part 1", JOptionPane.QUESTION_MESSAGE, null, CASE3_SUSPECTS, CASE3_SUSPECTS[0]);
    if (editor == null) return;
    String threat = (String) JOptionPane.showInputDialog(frame, "Second: who arranged the pressure campaign against Amara?",
        "Final Deduction — Part 2", JOptionPane.QUESTION_MESSAGE, null, CASE3_SUSPECTS, CASE3_SUSPECTS[0]);
    if (threat == null) return;
    boolean correctEditor = editor.startsWith("Rhea");
    boolean correctThreat = threat.startsWith("Celeste");
    int score = Math.min(100, nextCase3Clue * 10 + (correctEditor ? 25 : 0) + (correctThreat ? 25 : 0));
    StringBuilder report = new StringBuilder("CASE #003 — FINAL REPORT\n\n");
    report.append("Ledger altered by: ").append(editor).append("\nPressure campaign linked to: ").append(threat).append("\n\n");
    if (correctEditor && correctThreat) {
        case3Solved = true;
        report.append("CASE SOLVED — THE SILENT WITNESS\n\n"
            + "Rhea altered Copy B to make the payments look as if Daniel had redirected them. Celeste's broker account is linked to the pressure campaign designed to intimidate Amara and contain the inquiry. Malik shared the mismatch so it could be investigated; that action alone did not make him the forger.\n\n"
            + "THE VEIL FILES — SEASON ONE FINALE\n\n"
            + "Amara sends the signed original ledger, the audit history, and the recovered invoice to an independent review team. The checksum verifies the real records, Daniel is cleared of the false accusation, and the suspicious payments are referred for a formal investigation. Rhea's edits and Celeste's pressure campaign are exposed as separate acts, and neither can hide behind the other.\n\n"
            + "The P-17 references from the archive, the museum, and the ledger are finally compared. They are not a name or a signature belonging to one mastermind: they are a shared routing code used by a network that moved altered records between institutions. The evidence is enough to expose the network's method and begin tracing its members, but not enough to honestly name a single person at its center.\n\n"
            + "Amara publishes a carefully verified account, protecting the witnesses and showing exactly what the evidence proves. The archive's original page is restored, the Aster Clock case is corrected in the museum record, and the ledger is set straight. The Veil is not magically erased overnight—but it can no longer operate unseen.\n\n"
            + "FINAL MESSAGE: Three cases. Three truths recovered. The evidence has a voice now—and someone is finally listening.\n"
            + "THE VEIL FILES — CASE CLOSED.");
    } else {
        report.append("CASE PARTLY SOLVED\n\n")
            .append(correctEditor ? "You correctly identified Rhea's ledger edit.\n" : "The checksum and audit-terminal history point to Rhea as the person who altered Copy B.\n")
            .append(correctThreat ? "You correctly connected Celeste to the pressure campaign.\n" : "The relay payment and crisis-communications draft point to Celeste's account.\n")
            .append("Review the Evidence Journal and Deduction Board, then try again.");
    }
    report.append("\n\nEvidence inspected: ").append(nextCase3Clue).append("/").append(CASE3_CLUES.length)
        .append("\nDetective score: ").append(score).append("/100");
    story.setText(report.toString());
}

private static void restartCase3() {
    int choice = JOptionPane.showConfirmDialog(frame, "Restart The Silent Witness?", "Restart Case #003", JOptionPane.YES_NO_OPTION);
    if (choice == JOptionPane.YES_OPTION) {
        nextCase3Clue = 0;
        collectedCase3Clues.clear();
        case3Solved = false;
        showCase3();
    }
}

private static void restartCase2() {
    int choice = JOptionPane.showConfirmDialog(frame, "Restart The Thirteenth Hour?",
        "Restart Case #002", JOptionPane.YES_NO_OPTION);
    if (choice == JOptionPane.YES_OPTION) {
        nextCase2Clue = 0;
        collectedCase2Clues.clear();
        case2Solved = false;
        showCase2();
    }
}


}
