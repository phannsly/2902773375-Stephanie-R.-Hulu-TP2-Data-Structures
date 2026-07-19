import java.util.Scanner;

/**
 Class Node: merepresentasikan satu simpul (satu data buku) dalam
 Single Linked List. Setiap Node menyimpan data buku dan referensi ke Node berikutnya.
 */
class Node {
    String kodeBuku;
    String judul;
    String penulis;
    Node next; // referensi ke node berikutnya

    public Node(String kodeBuku, String judul, String penulis) {
        this.kodeBuku = kodeBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.next = null;
    }
}

/**
 Class LinkedList: mengelola kumpulan Node (daftar buku) dalam
 bentuk Single Linked List, beserta operasi tambah, hapus, cari,
 dan tampilkan.
 */
class LinkedList {
    private Node head; // penunjuk ke node pertama
    private Node tail;  // penunjuk ke node terakhir (mempercepat operasi tambah di akhir)
    private int jumlahBuku;

    public LinkedList() {
        head = null;
        tail = null;
        jumlahBuku = 0;
    }

    /**
     Tambah Buku (Push) - menambahkan data baru di akhir daftar.
     Validasi: kodeBuku maksimal 5 karakter.
     */
    public boolean tambahBuku(String kodeBuku, String judul, String penulis) {
        if (kodeBuku.length() > 5) {
            System.out.println("Gagal menambahkan: kode buku maksimal 5 karakter!");
            return false;
        }

        Node bukuBaru = new Node(kodeBuku, judul, penulis);

        if (head == null) {
            // daftar masih kosong, node baru menjadi head sekaligus tail
            head = bukuBaru;
            tail = bukuBaru;
        } else {
            // sambungkan node baru di belakang tail, lalu geser tail
            tail.next = bukuBaru;
            tail = bukuBaru;
        }
        jumlahBuku++;
        System.out.println("Data berhasil ditambahkan!");
        return true;
    }

    /**
     Hapus Buku (Pop) - menghapus data buku TERAKHIR dari daftar.
     Karena Single Linked List hanya punya pointer maju, untuk
     mencapai node kedua-dari-terakhir kita harus menelusuri dari head.
     */
    public void hapusBukuTerakhir() {
        if (head == null) {
            System.out.println("Tidak ada data untuk dihapus.");
            return;
        }

        if (head == tail) {
            // hanya ada satu buku dalam daftar
            System.out.println("Buku '" + head.judul + "' berhasil dihapus.");
            head = null;
            tail = null;
            jumlahBuku--;
            return;
        }

        // Menelusuri sampai node sebelum tail
        Node current = head;
        while (current.next != tail) {
            current = current.next;
        }

        System.out.println("Buku '" + tail.judul + "' berhasil dihapus.");
        current.next = null; // putuskan hubungan ke tail lama
        tail = current;      // node ini menjadi tail baru
        jumlahBuku--;
    }

    /**
     Cari Buku berdasarkan kodeBuku.
     */
    public void cariBuku(String kodeBuku) {
        Node current = head;
        while (current != null) {
            if (current.kodeBuku.equalsIgnoreCase(kodeBuku)) {
                System.out.println("Buku ditemukan!");
                System.out.println("Kode: " + current.kodeBuku +
                        " | Judul: " + current.judul +
                        " | Penulis: " + current.penulis);
                return;
            }
            current = current.next;
        }
        System.out.println("Buku tidak ditemukan.");
    }

    /**
     Tampilkan seluruh data buku sesuai urutan input, lalu total buku.
     */
    public void tampilkanSemua() {
        if (head == null) {
            System.out.println("Daftar buku masih kosong.");
            return;
        }

        System.out.println("Daftar Buku:");
        Node current = head;
        while (current != null) {
            System.out.println("Kode: " + current.kodeBuku +
                    " | Judul: " + current.judul +
                    " | Penulis: " + current.penulis);
            current = current.next;
        }
        System.out.println("Total Buku: " + jumlahBuku);
    }

    public int getJumlahBuku() {
        return jumlahBuku;
    }
}

/**
 Class utama: berisi menu interaktif untuk menguji seluruh operasi
 LinkedList (tambah, hapus, cari, tampil).
 */
public class PerpustakaanApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList daftarBuku = new LinkedList();
        int pilihan;

        do {
            System.out.println("\n===== SISTEM DATA BUKU =====");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Cari Buku");
            System.out.println("4. Lihat Semua Buku");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");

            // validasi input angka agar program tidak crash jika user salah input
            while (!scanner.hasNextInt()) {
                System.out.print("Input tidak valid, masukkan angka menu: ");
                scanner.next();
            }
            pilihan = scanner.nextInt();
            scanner.nextLine(); // buang sisa newline

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan Kode Buku (maks 5 karakter): ");
                    String kode = scanner.nextLine();
                    System.out.print("Masukkan Judul: ");
                    String judul = scanner.nextLine();
                    System.out.print("Masukkan Penulis: ");
                    String penulis = scanner.nextLine();
                    daftarBuku.tambahBuku(kode, judul, penulis);

                    if (daftarBuku.getJumlahBuku() < 5) {
                        System.out.println("Catatan: minimal data yang disarankan adalah 5 buku, saat ini baru " +
                                daftarBuku.getJumlahBuku() + " buku.");
                    }
                    break;

                case 2:
                    daftarBuku.hapusBukuTerakhir();
                    break;

                case 3:
                    System.out.print("Masukkan Kode Buku yang dicari: ");
                    String kodeCari = scanner.nextLine();
                    daftarBuku.cariBuku(kodeCari);
                    break;

                case 4:
                    daftarBuku.tampilkanSemua();
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