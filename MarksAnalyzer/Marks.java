import java.util.*;

class Student {
    private String name;
    private int[] marks;
    private float average;
    private int highest, lowest;
    private boolean passed;

    public Student(String name, int subjects) {
        this.name = name;
        marks = new int[subjects];
    }

    public String getName() { return name; }
    public int[] getMarks() { return marks; }
    public float getAverage() { return average; }
    public int getHighest() { return highest; }
    public int getLowest() { return lowest; }
    public boolean isPassed() { return passed; }

    public void inputMarks(Scanner sc) {
        System.out.println("\nEnter marks for " + name);
        for(int i=0; i<marks.length; i++) {
            int mark;
            do {
                System.out.print("Subject " + (i+1) + ": ");
                mark = sc.nextInt();
            } while(mark<0 || mark>100);
            marks[i] = mark;
        }
        sc.nextLine();
        calcStats();
    }

    private void calcStats() {
        int sum=0;
        highest = lowest = marks[0];
        passed = true;
        
        for(int m : marks) {
            sum += m;
            if(m > highest) highest = m;
            if(m < lowest) lowest = m;
            if(m < 35) passed = false;
        }
        average = (float)sum / marks.length;
    }
}

class MarksAnalyzer {
    private Student[] students;
    private int numSubs;

    public MarksAnalyzer(int nStudents, int nSubs) {
        students = new Student[nStudents];
        numSubs = nSubs;
    }

    public void getAllData(Scanner sc) {
        for(int i=0; i<students.length; i++) {
            System.out.print("Student " + (i+1) + " name: ");
            students[i] = new Student(sc.nextLine(), numSubs);
            students[i].inputMarks(sc);
        }
    }

    public void showResults() {
        System.out.println("\nStudent Results:");
        System.out.println("Name\t\tMarks\t\tAvg\tHigh\tLow\tStatus");
        System.out.println("-------------------------------------------------------");

        for(Student s : students) {
            System.out.println(s.getName() + "\t\t" + 
                             java.util.Arrays.toString(s.getMarks()) + "\t" +
                             s.getAverage() + "\t" + s.getHighest() + "\t" +
                             s.getLowest() + "\t" + (s.isPassed()?"Pass":"Fail"));
        }
    }

    public void findTopper() {
        Student top = students[0];
        for(Student s : students) {
            if(s.getAverage() > top.getAverage()) top = s;
        }
        System.out.println("\nTopper: " + top.getName() + " (" + 
                          top.getAverage() + ")");
    }

    public void showFailed() {
        System.out.print("\nFailed: ");
        boolean any = false;
        for(Student s : students) {
            if(!s.isPassed()) {
                System.out.print(s.getName() + " ");
                any = true;
            }
        }
        if(!any) System.out.print("None");
    }

    public void rankings() {
        Student[] copy = java.util.Arrays.copyOf(students, students.length);
        java.util.Arrays.sort(copy, (a,b) -> Float.compare(b.getAverage(), a.getAverage()));
        
        System.out.println("\nRankings:");
        for(int i=0; i<copy.length; i++) {
            System.out.println((i+1) + ". " + copy[i].getName() + " - " + copy[i].getAverage());
        }
    }

    public void subjectWise() {
        System.out.println("\nSubject wise top/low:");
        for(int sub=0; sub<numSubs; sub++) {
            int high=-1, low=101;
            String hname="", lname="";
            for(Student s : students) {
                int m = s.getMarks()[sub];
                if(m > high) { high=m; hname=s.getName(); }
                if(m < low) { low=m; lname=s.getName(); }
            }
            System.out.println("Sub" + (sub+1) + ": " + hname + "(" + high + ") / " + 
                             lname + "(" + low + ")");
        }
    }

    public void passStats() {
        int pass=0;
        for(Student s : students) if(s.isPassed()) pass++;
        System.out.println("\nPass %: " + (pass*100.0/students.length) + "%");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("No. students: ");
        int nstud = sc.nextInt();
        System.out.print("No. subjects: ");
        int nsub = sc.nextInt();
        sc.nextLine();

        MarksAnalyzer ma = new MarksAnalyzer(nstud, nsub);
        ma.getAllData(sc);

        ma.showResults();
        ma.findTopper();
        ma.showFailed();
        ma.rankings();
        ma.subjectWise();
        ma.passStats();

        sc.close();
    }
}
