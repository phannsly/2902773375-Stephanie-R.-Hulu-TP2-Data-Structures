import java.util.Scanner;

/**
 Class Node: menyimpan data satu pelanggan (dipakai bersama oleh
 Queue maupun Stack).
 */
class NodePelanggan {
    String nomorAntrian;
    String namaPelanggan;
    double totalBelanja;
    NodePelanggan next;

    public NodePelanggan(String nomorAntrian, String namaPelanggan, double totalBelanja) {
        this.nomorAntrian = nomorAntrian;
        this.namaPelanggan = namaPelanggan;
        this.totalBelanja = totalBelanja;
        this.next = null;
    }
}

/**
 Class Queue: menyimpan antrian pelanggan (FIFO - First In First Out).
 Pelanggan yang lebih dulu masuk, lebih dulu dilayani.
 */
class Queue {
    private NodePelanggan front; // pelanggan paling depan (akan dilayani lebih dulu)
    private NodePelanggan rear;  // pelanggan paling belakang (baru masuk)
    private int jumlahAntrian;
    private static final int MAKS_ANTRIAN = 5; // batas jumlah pelanggan dalam antrian

    public Queue() {
        front = null;
        rear = null;
        jumlahAntrian = 0;
    }

    /**
     Enqueue: menambahkan pelanggan baru ke akhir antrian.
     */
    public boolean enqueue(String nomorAntrian, String namaPelanggan, double totalBelanja) {
        if (jumlahAntrian >= MAKS_ANTRIAN) {
            System.out.println("Antrian penuh! Maksimal " + MAKS_ANTRIAN + " pelanggan.");
            return false;
        }

        NodePelanggan baru = new NodePelanggan(nomorAntrian, namaPelanggan, totalBelanja);
        if (front == null) {
            front = baru;
            rear = baru;
        } else {
            rear.next = baru;
            rear = baru;
        }
        jumlahAntrian++;
        System.out.println("Data pelanggan ditambahkan ke antrian!");
        return true;
    }

    /**
     Dequeue: mengeluarkan pelanggan paling depan dari antrian
     (pelanggan yang akan dilayani). Mengembalikan null jika kosong.
     */
    public NodePelanggan dequeue() {
        if (front == null) {
            System.out.println("Antrian kosong, tidak ada pelanggan untuk dilayani.");
            return null;
        }
        NodePelanggan dilayani = front;
        front = front.next;
        if (front == null) {
            rear = null; // antrian menjadi kosong
        }
        jumlahAntrian--;
        return dilayani;
    }

    public boolean isEmpty() {
        return front == null;
    }

    /**
     Menampilkan seluruh pelanggan yang masih ada dalam antrian.
     */
    public void tampilkanAntrian() {
        if (front == null) {
            System.out.println("Antrian kosong.");
            return;
        }
        System.out.println("Antrian Pelanggan Saat Ini:");
        NodePelanggan current = front;
        while (current != null) {
            System.out.println("No: " + current.nomorAntrian +
                    " | Nama: " + current.namaPelanggan +
                    " | Total: " + current.totalBelanja);
            current = current.next;
        }
    }
}

/**
 Class Stack: menyimpan riwayat transaksi pelanggan yang sudah
 dilayani (LIFO - Last In First Out). Transaksi terbaru berada
 paling atas.
 */
class Stack {
    private NodePelanggan top; // puncak stack (transaksi paling baru)
    private int jumlahTransaksi;

    public Stack() {
        top = null;
        jumlahTransaksi = 0;
    }

    /**
     Push: menyimpan data transaksi pelanggan yang baru selesai dilayani
     ke puncak stack.
     */
    public void push(NodePelanggan pelanggan) {
        pelanggan.next = top; // node baru menunjuk ke node lama di puncak
        top = pelanggan;      // node baru menjadi puncak
        jumlahTransaksi++;
        System.out.println("Transaksi disimpan ke riwayat.");
    }

    /**
     Menampilkan riwayat transaksi dari yang terbaru ke yang paling lama.
     */
    public void tampilkanRiwayat() {
        if (top == null) {
            System.out.println("Belum ada riwayat transaksi.");
            return;
        }
        System.out.println("Riwayat Transaksi (terbaru ke lama):");
        NodePelanggan current = top;
        while (current != null) {
            System.out.println("No: " + current.nomorAntrian +
                    " | Nama: " + current.namaPelanggan +
                    " | Total: " + current.totalBelanja);
            current = current.next;
        }
        System.out.println("Total Transaksi: " + jumlahTransaksi);
    }
}

/**
 Class utama: menu interaktif sistem kasir toko yang menggabungkan
 Queue (antrian pelanggan) dan Stack (riwayat transaksi).
 */
public class KasirTokoApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Queue antrian = new Queue();
        Stack riwayat = new Stack();
        int pilihan;

        do {
            System.out.println("\n=== SISTEM KASIR TOKO ===");
            System.out.println("1. Tambah Antrian");
            System.out.println("2. Layani Pelanggan");
            System.out.println("3. Tampilkan Antrian");
            System.out.println("4. Lihat Riwayat Transaksi");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Input tidak valid, masukkan angka menu: ");
                scanner.next();
            }
            pilihan = scanner.nextInt();
            scanner.nextLine(); // buang sisa newline

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan Nomor Antrian: ");
                    String nomor = scanner.nextLine();
                    System.out.print("Masukkan Nama Pelanggan: ");
                    String nama = scanner.nextLine();
                    System.out.print("Masukkan Total Belanja: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.print("Input tidak valid, masukkan angka: ");
                        scanner.next();
                    }
                    double total = scanner.nextDouble();
                    scanner.nextLine();
                    antrian.enqueue(nomor, nama, total);
                    break;

                case 2:
                    NodePelanggan dilayani = antrian.dequeue();
                    if (dilayani != null) {
                        System.out.println("Melayani pelanggan " + dilayani.nomorAntrian +
                                " (" + dilayani.namaPelanggan + ")");
                        riwayat.push(dilayani);
                    }
                    break;

                case 3:
                    antrian.tampilkanAntrian();
                    break;

                case 4:
                    riwayat.tampilkanRiwayat();
                    break;

                case 5:
                    System.out.println("Terima kasih, program selesai.");
                    break;

                default:
                    System.out.println("Menu tidak valid, silakan pilih 1-5.");
            }
        } while (pilihan != 5);

        scanner.close();
    }
}