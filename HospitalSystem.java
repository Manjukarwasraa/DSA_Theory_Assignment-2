import java.util.*;

// --- PATIENT NODE CLASS ---
class Patient {
    int patientID;
    String name;
    String admissionDate;
    String treatmentDetails;
    int priority;
    Patient next;

    Patient(int id, String n, String date, String details, int pr) {
        patientID = id;
        name = n;
        admissionDate = date;
        treatmentDetails = details;
        priority = pr;
        next = null;
    }
}

// --- LINKED LIST CLASS ---
class PatientLinkedList {
    Patient head = null;

    void insertPatient(Patient p) {
        if (head == null) {
            head = p;
        } else {
            Patient temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = p;
        }
        System.out.println(" Patient added successfully: " + p.name);
    }

    void deletePatient(int id) {
        if (head == null) {
            System.out.println(" No records to delete.");
            return;
        }
        if (head.patientID == id) {
            head = head.next;
            System.out.println(" Patient deleted successfully!");
            return;
        }
        Patient temp = head;
        while (temp.next != null && temp.next.patientID != id)
            temp = temp.next;
        if (temp.next == null)
            System.out.println(" Patient not found!");
        else {
            temp.next = temp.next.next;
            System.out.println(" Patient deleted successfully!");
        }
    }

    Patient retrievePatient(int id) {
        Patient temp = head;
        while (temp != null) {
            if (temp.patientID == id)
                return temp;
            temp = temp.next;
        }
        return null;
    }

    void displayAll() {
        if (head == null) {
            System.out.println(" No patient records found.");
            return;
        }
        Patient temp = head;
        System.out.println("\n Current Patients:");
        while (temp != null) {
            System.out.println("ID: " + temp.patientID + " | Name: " + temp.name +
                    " | Date: " + temp.admissionDate +
                    " | Treatment: " + temp.treatmentDetails +
                    " | Priority: " + temp.priority);
            temp = temp.next;
        }
    }
}

// -------------------- STACK CLASS --------------------
class UndoStack {
    Stack<Patient> stack = new Stack<>();

    void push(Patient p) {
        stack.push(p);
        System.out.println(" Operation recorded for undo: " + p.name);
    }

    Patient pop() {
        if (stack.isEmpty()) {
            System.out.println(" Nothing to undo!");
            return null;
        }
        Patient p = stack.pop();
        System.out.println(" Undo: Removed " + p.name + " from records.");
        return p;
    }
}

// --- PRIORITY QUEUE CLASS ---
class EmergencyQueue {
    PriorityQueue<Patient> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.priority));

    void enqueue(Patient p) {
        pq.add(p);
        System.out.println(" Emergency patient added: " + p.name);
    }

    void process() {
        if (pq.isEmpty()) {
            System.out.println(" No emergency patients.");
            return;
        }
        Patient p = pq.poll();
        System.out.println(" Processing emergency patient: " + p.name);
    }
}

// --- BILLING (POLYNOMIAL) ---
class Billing {
    void calculateBilling(int[] coeffs, int[] exps, int x) {
        int total = 0;
        System.out.print("Billing polynomial: ");
        for (int i = 0; i < coeffs.length; i++) {
            System.out.print(coeffs[i] + "x^" + exps[i]);
            if (i < coeffs.length - 1) System.out.print(" + ");
            total += coeffs[i] * Math.pow(x, exps[i]);
        }
        System.out.println("\n Total Bill Amount: " + total);
    }
}

// --- POSTFIX INVENTORY ---
class Inventory {
    int evaluatePostfix(String expr) {
        Stack<Integer> stack = new Stack<>();
        for (char c : expr.toCharArray()) {
            if (Character.isDigit(c))
                stack.push(c - '0');
            else {
                int b = stack.pop();
                int a = stack.pop();
                switch (c) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                }
            }
        }
        return stack.pop();
    }
}

// --- MAIN CLASS ---
public class HospitalSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PatientLinkedList list = new PatientLinkedList();
        UndoStack undo = new UndoStack();
        EmergencyQueue eq = new EmergencyQueue();
        Billing bill = new Billing();
        Inventory inv = new Inventory();

        while (true) {
            System.out.println("\n---  HOSPITAL MANAGEMENT MENU ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Delete Patient");
            System.out.println("3. Retrieve Patient");
            System.out.println("4. Undo Last Admission");
            System.out.println("5. Emergency Queue");
            System.out.println("6. Calculate Billing");
            System.out.println("7. Evaluate Inventory");
            System.out.println("8. Display All");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Admission Date (dd/mm/yyyy): ");
                    String date = sc.nextLine();
                    System.out.print("Enter Treatment Details: ");
                    String details = sc.nextLine();
                    System.out.print("Enter Priority (1 for emergency): ");
                    int pr = sc.nextInt();

                    Patient p = new Patient(id, name, date, details, pr);
                    list.insertPatient(p);
                    undo.push(p);
                    if (pr == 1) eq.enqueue(p);
                    break;

                case 2:
                    System.out.print("Enter ID to delete: ");
                    int did = sc.nextInt();
                    list.deletePatient(did);
                    break;

                case 3:
                    System.out.print("Enter ID to retrieve: ");
                    int rid = sc.nextInt();
                    Patient found = list.retrievePatient(rid);
                    if (found != null)
                        System.out.println(" Found: " + found.name + " | " + found.treatmentDetails);
                    else
                        System.out.println(" Patient not found.");
                    break;

                case 4:
                    undo.pop();
                    break;

                case 5:
                    eq.process();
                    break;

                case 6:
                    int[] coeff = {3, 2, 1};
                    int[] exp = {2, 1, 0};
                    bill.calculateBilling(coeff, exp, 2);
                    break;

                case 7:
                    System.out.print("Enter postfix expression (like 23+5*): ");
                    String expr = sc.next();
                    System.out.println(" Inventory Result: " + inv.evaluatePostfix(expr));
                    break;

                case 8:
                    list.displayAll();
                    break;

                case 9:
                    System.out.println(" Exiting system...");
                    return;

                default:
                    System.out.println(" Invalid choice.");
            }
        }
    }
}
