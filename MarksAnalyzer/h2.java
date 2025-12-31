import java.util.*;
class Student {
    private String name;
    private int[] marks;          
    private float average;        
    private int highest, lowest;  
    private boolean isPassed;     

    
      Constructor to initialize a student with their name and number of subjects.
      @param name student name
      @param subjects number of subjects
     
    public Student(String name, int subjects) {
        this.name = name;
        this.marks = new int[subjects];
    }

    public String getName() { return name; }
    public int[] getMarks() { return marks; }
    public float getAverage() { return average; }
    public int getHighest() { return highest; }
    public int getLowest() { return lowest; }
    public boolean isPassed() { return isPassed; }

    public void enterMarks(Scanner sc) {
        System.out.println("\n--- Enter marks for " + name + " ---");
        for (int i = 0; i < marks.length; i++) {
            while (true) {
                try {
                    System.out.print("Subject " + (i+1) + " Marks (0-100): ");
                    int mark = sc.nextInt();

                    if (mark < 0 || mark > 100) {
                        throw new IllegalArgumentException("Marks must be between 0 and 100.");
                    }
                    marks[i] = mark;
                    break;
                } catch (InputMismatchException ime) {
                    System.out.println("⚠ Invalid input! Please enter an integer.");
                    sc.nextLine(); // Clear buffer
                } catch (IllegalArgumentException iae) {
                    System.out.println("⚠ " + iae.getMessage());
                }
            }
        }
        sc.nextLine(); 
        calculateStatistics();
    }

    
    private void calculateStatistics() {
        int sum = 0;
        highest = marks[0];
        lowest = marks[0];
        isPassed = true;

        for (int mark : marks) {
            sum += mark;
            if (mark > highest) highest = mark;
            if (mark < lowest) lowest = mark;
            if (mark < 35) isPassed = false;
        }

        average = (float) sum / marks.length;
    }
}

class MarksAnalyzer {
    private Student[] students;          
    private int numSubjects;           

    public MarksAnalyzer(int numStudents, int numSubjects) {
        this.students = new Student[numStudents];
        this.numSubjects = numSubjects;
    }

   
      @param sc Scanner object for input.
    
    public void inputStudentData(Scanner sc) {
        System.out.println("\n=== Input Student Data ===");
        for (int i = 0; i < students.length; i++) {
            System.out.print("Enter name of Student " + (i + 1) + ": ");
            String name = sc.nextLine().trim();
            students[i] = new Student(name, numSubjects);
            students[i].enterMarks(sc);
        }
    }

   
     
    public void displayResults() {
        System.out.println("\n\n===== Student Results =====");
        System.out.printf("%-15s %-25s %-10s %-10s %-10s %-10s\n",
            "Name", "Marks (Subjects)", "Average", "Highest", "Lowest", "Status");
        System.out.println("--------------------------------------------------------------------------------");

        for (Student s : students) {
            System.out.printf("%-15s %-25s %-10.2f %-10d %-10d %-10s\n",
                s.getName(),
                Arrays.toString(s.getMarks()),
                s.getAverage(),
                s.getHighest(),
                s.getLowest(),
                s.isPassed() ? "✅ Passed" : "❌ Failed");
        }
    }

    public void showTopper() {
        Student topper = students[0];
        for (Student s : students) {
            if (s.getAverage() > topper.getAverage()) {
                topper = s;
            }
        }
        System.out.println("\n🏆 Topper: " + topper.getName() + " with average " + String.format("%.2f", topper.getAverage()));
    }

    public void showFailedStudents() {
        System.out.println("\n❌ Failed Students:");
        boolean found = false;
        for (Student s : students) {
            if (!s.isPassed()) {
                System.out.println("- " + s.getName());
                found = true;
            }
        }
        if (!found) System.out.println("None 🎉");
    }

    public void displayRankings() {
      
        Student[] ranked = Arrays.copyOf(students, students.length);

        Arrays.sort(ranked, (a, b) -> Float.compare(b.getAverage(), a.getAverage()));

        System.out.println("\n🏅 Class Ranking:");
        System.out.printf("%-5s %-15s %-10s\n", "Rank", "Name", "Average");
        System.out.println("-------------------------------");

        for (int i = 0; i < ranked.length; i++) {
            System.out.printf("%-5d %-15s %-10.2f\n", i+1, ranked[i].getName(), ranked[i].getAverage());
        }
    }

    public void showSubjectTopLow() {
        System.out.println("\n📊 Subject-wise Highest and Lowest Scorers:");
        for (int sub = 0; sub < numSubjects; sub++) {
            int highestMark = -1;
            int lowestMark = 101;
            String highestScorer = "";
            String lowestScorer = "";

            for (Student s : students) {
                int mark = s.getMarks()[sub];
                if (mark > highestMark) {
                    highestMark = mark;
                    highestScorer = s.getName();
                }
                if (mark < lowestMark) {
                    lowestMark = mark;
                    lowestScorer = s.getName();
                }
            }
            System.out.printf("Subject %d -> Highest: %s (%d), Lowest: %s (%d)\n",
                sub + 1, highestScorer, highestMark, lowestScorer, lowestMark);
        }
    }

    public void showPassStatistics() {
        int total = students.length;
        int passedCount = 0;
        for (Student s : students) {
            if (s.isPassed()) passedCount++;
        }
        float passPercentage = (float) passedCount / total * 100;
        System.out.printf("\n📈 Pass Percentage: %.2f%% (%d out of %d students passed)\n",
                          passPercentage, passedCount, total);
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("               🎓 Student Marks Analyzer 🎓");
        System.out.println("===============================================\n");

        Scanner sc = new Scanner(System.in);

        int numStudents = 0, numSubjects = 0;

        while (true) {
            try {
                System.out.print("Enter number of students: ");
                numStudents = sc.nextInt();
                if (numStudents <= 0) {
                    System.out.println("⚠ Number of students must be positive.");
                } else {
                    break;
                }
            } catch (InputMismatchException ime) {
                System.out.println("⚠ Invalid input! Please enter an integer.");
                sc.nextLine(); // clear buffer
            }
        }

        while (true) {
            try {
                System.out.print("Enter number of subjects: ");
                numSubjects = sc.nextInt();
                if (numSubjects <= 0) {
                    System.out.println("⚠ Number of subjects must be positive.");
                } else {
                    break;
                }
            } catch (InputMismatchException ime) {
                System.out.println("⚠ Invalid input! Please enter an integer.");
                sc.nextLine(); // clear buffer
            }
        }
        sc.nextLine(); 

     
        MarksAnalyzer analyzer = new MarksAnalyzer(numStudents, numSubjects);


        analyzer.inputStudentData(sc);

       
        analyzer.displayResults();

 
        analyzer.showTopper();

    
        analyzer.showFailedStudents();

     
        analyzer.displayRankings();

        
        analyzer.showSubjectTopLow();

       
        analyzer.showPassStatistics();

        System.out.println("\n===============================================");
        System.out.println("           ✅ Analysis Completed Successfully ✅");
        System.out.println("===============================================\n");

        sc.close();
    }
}