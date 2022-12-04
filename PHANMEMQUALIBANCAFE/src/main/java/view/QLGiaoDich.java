/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import domainmodel.NhaCungCap;
import domainmodel.NhanVien;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import repository.HoaDonChiTietRepo;
import repository.KhuVucRepository;
import service.IBanHangService;
import service.IBanService;
import service.IHoaDon;
import service.IHoaDonChiTiet;
import service.IKhuVucService;
import service.ILogin;
import service.IPhieuNhap;
import service.IPhieuTra;
import service.implement.BanHangService;
import service.implement.BanService;
import service.implement.ChiTietHoaDonService;
import service.implement.HoaDonService;
import service.implement.LoginSerVice;
import service.implement.PhieuNhapService;
import service.implement.PhieuTraService;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.ChiTietPhieuNhapViewModel;
import viewmodel.ChiTietPhieuTraViewModel;
import viewmodel.ChitietHoaDonViewModel;
import viewmodel.HoaDonViewModel;
import viewmodel.KhuVucViewModel;
import viewmodel.NguyenLieuViewModel_Hoang;
import viewmodel.NhaCungCapViewModel_Hoang;
import viewmodel.NhanVienViewModel_Hoang;
import viewmodel.PhieuNhapViewModel;
import viewmodel.PhieuTraViewModel;

/**
 *
 * @author trant
 */
public class QLGiaoDich extends javax.swing.JPanel {

    /**
     * Creates new form QLGiaoDich
     */
    DefaultTableModel modelPhieuNhap = new DefaultTableModel();
    DefaultTableModel modelChiTietPhieuNhap = new DefaultTableModel();
    DefaultTableModel modelPhieuTra = new DefaultTableModel();
    DefaultTableModel modelChiTietPhieuTra = new DefaultTableModel();
    DefaultTableModel modelHoaDon = new DefaultTableModel();
    DefaultTableModel modelHDCT = new DefaultTableModel();
    DefaultTableModel modelNguyenLieu = new DefaultTableModel();
    DefaultTableModel modelNguyenLieuTra = new DefaultTableModel();
    DefaultComboBoxModel<NhaCungCapViewModel_Hoang> comboNhaCungCap;
    DefaultComboBoxModel<NhanVienViewModel_Hoang> comboNhanVien;
    DefaultComboBoxModel<NguyenLieuViewModel_Hoang> comboNguyenLieu;
    DefaultComboBoxModel<NhaCungCapViewModel_Hoang> comboNhaCungCapTra;
    DefaultComboBoxModel<NhanVienViewModel_Hoang> comboNhanVienTra;
    DefaultComboBoxModel<NguyenLieuViewModel_Hoang> comboNguyenLieuTra;
    DefaultComboBoxModel<ChiNhanhViewModel_Hoang> comboChiNhanh;
    DefaultComboBoxModel<ChiNhanhViewModel_Hoang> comboChiNhanhTra;
    IHoaDon hoaDonService = new HoaDonService();
    IHoaDonChiTiet hoaDonChiTietService = new ChiTietHoaDonService();
    List<HoaDonViewModel> lstHoaDon = new ArrayList<>();
    List<ChitietHoaDonViewModel> lstChiTietHD = new ArrayList();

    IPhieuNhap phieuNhapSevice = new PhieuNhapService();
    Set<ChiTietPhieuNhapViewModel> lstChiTietPhieuNhap = new HashSet<>();
    Set<PhieuNhapViewModel> lstPhieuNhap = new HashSet<>();

    IPhieuTra phieuTraService = new PhieuTraService();
    Set<PhieuTraViewModel> lstPhieuTra = new HashSet<>();
    IBanHangService iBanHang = new BanHangService();
    IBanService iBanService = new BanService();
    private TaiKhoanAdmin taiKhoanAdmin;
    private TaiKhoanNguoiDung taiKhoanNguoiDung;
    Set<ChiNhanhViewModel_Hoang> lstChiNhanh = new HashSet<>();

    public QLGiaoDich(TaiKhoanAdmin admin, TaiKhoanNguoiDung nguoiDung) {
        initComponents();
        taiKhoanAdmin = admin;
        taiKhoanNguoiDung = nguoiDung;
        modelNguyenLieu = (DefaultTableModel) tblNguyenLieu.getModel();
        modelNguyenLieuTra = (DefaultTableModel) tblNguyenLieuTra.getModel();

        comboNhaCungCap = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhaCungCap().toArray());
        cboNhaCungCapNhap.setModel((DefaultComboBoxModel) comboNhaCungCap);

        comboNhaCungCapTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhaCungCap().toArray());
        cboNhaCungCapTra.setModel((DefaultComboBoxModel) comboNhaCungCapTra);
        if (taiKhoanAdmin != null) {
            //Chi nhánh
            comboChiNhanh = (DefaultComboBoxModel) new DefaultComboBoxModel<>(iBanHang.getAllChiNhanh().toArray());
            cboChiNhanhNhap.setModel((DefaultComboBoxModel) comboChiNhanh);
            comboChiNhanhTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(iBanHang.getAllChiNhanh().toArray());
            cboChiNhanhTra.setModel((DefaultComboBoxModel) comboChiNhanhTra);
            //Nhân viên
            comboNhanVien = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhanVienByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()).toArray());
            cboNhanVienNhap.setModel((DefaultComboBoxModel) comboNhanVien);
            comboNhanVienTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhanVienByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()).toArray());
            cboNhanVienTra.setModel((DefaultComboBoxModel) comboNhanVienTra);
            //Nguyên liệu
            comboNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNguyenLieuByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()).toArray());
            cboNguyenLieuNhap.setModel((DefaultComboBoxModel) comboNguyenLieu);
            comboNguyenLieuTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNguyenLieuByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()).toArray());
            cboNguyenLieuTra.setModel((DefaultComboBoxModel) comboNguyenLieuTra);
            loadAll((((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()));
           

        } else {
            lblCNNhap.setVisible(false);
            cboChiNhanhNhap.setVisible(false);
            lblCNTra.setVisible(false);
            cboChiNhanhTra.setVisible(false);
            comboNhanVien = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhanVienByChiNhanh(iBanService.getChiNhanhByTaiKhoan(nguoiDung.getId()).getId()).toArray());
            cboNhanVienNhap.setModel((DefaultComboBoxModel) comboNhanVien);
            
            comboNhanVienTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhanVienByChiNhanh(iBanService.getChiNhanhByTaiKhoan(nguoiDung.getId()).getId()).toArray());
            cboNhanVienTra.setModel((DefaultComboBoxModel) comboNhanVienTra);
            
            comboNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNguyenLieuByChiNhanh(iBanService.getChiNhanhByTaiKhoan(nguoiDung.getId()).getId()).toArray());
            cboNguyenLieuNhap.setModel((DefaultComboBoxModel) comboNguyenLieu);
            
            comboNguyenLieuTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNguyenLieuByChiNhanh(iBanService.getChiNhanhByTaiKhoan(nguoiDung.getId()).getId()).toArray());
            cboNguyenLieuTra.setModel((DefaultComboBoxModel) comboNguyenLieuTra);
            loadAll(iBanService.getChiNhanhByTaiKhoan(nguoiDung.getId()).getId());
        }
    }

    private void loadAll(String idChiNhanh) {
        lstHoaDon = hoaDonService.getAllHoaDon();
        loadTableHoaDon(lstHoaDon);
        lstPhieuNhap = phieuNhapSevice.getAllPhieuNhapByChiNhanh(idChiNhanh);
        loadTablePhieuNhap(lstPhieuNhap);
        lstPhieuTra = phieuTraService.getAllPhieuTraByChiNhanh(idChiNhanh);
        loadTablePhieuTra(lstPhieuTra);
    }

    private void loadTableHoaDon(List<HoaDonViewModel> lstHoaDon) {
        modelHoaDon = (DefaultTableModel) tblHoaDon.getModel();
        modelHoaDon.setRowCount(0);
        for (HoaDonViewModel x : lstHoaDon) {
            modelHoaDon.addRow(x.getDataHoaDonView());
        }
    }

    private void loadTablePhieuNhap(Set<PhieuNhapViewModel> lstPhieuNhap) {
        modelPhieuNhap = (DefaultTableModel) tblPhieuNhap.getModel();
        modelPhieuNhap.setRowCount(0);
        for (PhieuNhapViewModel x : lstPhieuNhap) {
            modelPhieuNhap.addRow(x.getDataPhieuNhapView());
        }
    }

    private void loadTablePhieuTra(Set<PhieuTraViewModel> lstPhieuTra) {
        modelPhieuTra = (DefaultTableModel) tblPhieuTra.getModel();
        modelPhieuTra.setRowCount(0);
        for (PhieuTraViewModel x : lstPhieuTra) {
            modelPhieuTra.addRow(x.getPhieuTrahangView());
        }
    }

    private void fillTablePhieuTra(int index) {

        if (tblPhieuTra.getRowCount() >= 0) {
            txtMaPhieuTra.setText((String) tblPhieuTra.getValueAt(index, 1).toString());
            for (int i = 0; i < phieuNhapSevice.getAllNhaCungCap().size(); i++) {
                if (phieuNhapSevice.getAllNhaCungCap().get(i).getMa().equals(tblPhieuTra.getValueAt(index, 2))) {
                    cboNhaCungCapTra.setSelectedIndex(i);
                }
            }
            for (int i = 0; i < phieuNhapSevice.getAllNhaCungCap().size(); i++) {
                if (phieuNhapSevice.getAllNhaCungCap().get(i).getTen().equals(tblPhieuTra.getValueAt(index, 3))) {
                    cboNhaCungCapTra.setSelectedIndex(i);
                }
            }
            for (int i = 0; i < phieuNhapSevice.getAllNhanVien().size(); i++) {
                if (phieuNhapSevice.getAllNhanVien().get(i).getMa().equals(tblPhieuTra.getValueAt(index, 4))) {
                    cboNhanVienTra.setSelectedIndex(i);
                }
            }
            for (int i = 0; i < phieuNhapSevice.getAllNhanVien().size(); i++) {
                if (phieuNhapSevice.getAllNhanVien().get(i).getHoTen().equals(tblPhieuTra.getValueAt(index, 5))) {
                    cboNhanVienTra.setSelectedIndex(i);
                }
            }
            dateNgayTra.setDate((Date) tblPhieuTra.getValueAt(index, 6));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();
        btnLocHoaDon = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        dateFrom = new com.toedter.calendar.JDateChooser();
        jPanel7 = new javax.swing.JPanel();
        dateTo = new com.toedter.calendar.JDateChooser();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        rdoHuyPhieuNhap = new javax.swing.JRadioButton();
        rdoHoanThanhPhieuNhap = new javax.swing.JRadioButton();
        btnHoanThanhPhieuNhap = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        txtTimKiemPhieuNhap = new javax.swing.JTextField();
        btnTimKiemPhieuNhap = new javax.swing.JButton();
        rdoPhieuNhapTam = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        btnCapNhatPhieuNhap = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuNhap = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblPhieuNhapChiTiet = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        cboNguyenLieuNhap = new javax.swing.JComboBox<>();
        cboNhaCungCapNhap = new javax.swing.JComboBox<>();
        cboNhanVienNhap = new javax.swing.JComboBox<>();
        btnTaoPhieuNhap = new javax.swing.JButton();
        btnHuyPhieuNhap = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        txtMaPhieuNhap = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblNguyenLieu = new javax.swing.JTable();
        dateNgayNhap = new com.toedter.calendar.JDateChooser();
        cboChiNhanhNhap = new javax.swing.JComboBox<>();
        lblCNNhap = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChiTietPhieuTra = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        txtTimKiemPhieuTra = new javax.swing.JTextField();
        rdoHuyPhieuTra = new javax.swing.JRadioButton();
        rdoHoanThanhPhieuTra = new javax.swing.JRadioButton();
        btnTimKiemPhieuTra = new javax.swing.JButton();
        btnHoanThanhPhieuTra = new javax.swing.JButton();
        rdoPhieuTraTam = new javax.swing.JRadioButton();
        rdoTatCaPhieuTra = new javax.swing.JRadioButton();
        btnCapNhatPhieuTra = new javax.swing.JButton();
        btnExport1 = new javax.swing.JButton();
        btnImport1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPhieuTra = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        cboNguyenLieuTra = new javax.swing.JComboBox<>();
        cboNhaCungCapTra = new javax.swing.JComboBox<>();
        cboNhanVienTra = new javax.swing.JComboBox<>();
        btnTaoPhieuTra = new javax.swing.JButton();
        btnHuyPhieuTra = new javax.swing.JButton();
        dateNgayTra = new com.toedter.calendar.JDateChooser();
        jLabel40 = new javax.swing.JLabel();
        txtMaPhieuTra = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblNguyenLieuTra = new javax.swing.JTable();
        lblCNTra = new javax.swing.JLabel();
        cboChiNhanhTra = new javax.swing.JComboBox<>();

        jTabbedPane1.setBackground(new java.awt.Color(228, 212, 189));
        jTabbedPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(228, 212, 189));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        tblHoaDonChiTiet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng mua", "Giá bán", "Thành tiền", "Thành tiền sau KM"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonChiTiet.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tblHoaDonChiTiet);

        btnLocHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLocHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_conversion_30px.png"))); // NOI18N
        btnLocHoaDon.setText("Lọc hóa đơn");
        btnLocHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocHoaDonActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Từ ngày", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        dateFrom.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đến ngày", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        dateTo.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dateTo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLocHoaDon)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(btnLocHoaDon))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        jPanel10.setBackground(new java.awt.Color(228, 212, 189));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Ngày tạo", "Mã nhân viên", "Tên nhân viên", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.getTableHeader().setReorderingAllowed(false);
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblHoaDon);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 836, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 35, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hóa đơn", jPanel1);

        jPanel11.setBackground(new java.awt.Color(228, 212, 189));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        buttonGroup1.add(rdoHuyPhieuNhap);
        rdoHuyPhieuNhap.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoHuyPhieuNhap.setText("Đã hủy");
        rdoHuyPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHuyPhieuNhapActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoHoanThanhPhieuNhap);
        rdoHoanThanhPhieuNhap.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoHoanThanhPhieuNhap.setText("Đã hoàn thành");
        rdoHoanThanhPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHoanThanhPhieuNhapActionPerformed(evt);
            }
        });

        btnHoanThanhPhieuNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHoanThanhPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_ok_30px.png"))); // NOI18N
        btnHoanThanhPhieuNhap.setText("Hoàn thành");
        btnHoanThanhPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoanThanhPhieuNhapActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel32.setText("Nhập mã phiếu:");

        btnTimKiemPhieuNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiemPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search_20px.png"))); // NOI18N
        btnTimKiemPhieuNhap.setText("Tìm kiếm");
        btnTimKiemPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemPhieuNhapActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoPhieuNhapTam);
        rdoPhieuNhapTam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoPhieuNhapTam.setText("Phiếu tạm");
        rdoPhieuNhapTam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPhieuNhapTamActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButton1.setText("Tất cả");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        btnCapNhatPhieuNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhatPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_sync_30px.png"))); // NOI18N
        btnCapNhatPhieuNhap.setText("Cập nhật phiếu nhập");
        btnCapNhatPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatPhieuNhapActionPerformed(evt);
            }
        });

        btnExport.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_microsoft_excel_30px.png"))); // NOI18N
        btnExport.setText("Xuất excel");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnImport.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_microsoft_excel_30px.png"))); // NOI18N
        btnImport.setText("Mở excel");
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        tblPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã phiếu nhập", "Mã nhà cung cấp", "Tên nhà cung cấp", "Mã nhân viên", "Tên nhân viên", "Ngày nhập", "Trạng thái"
            }
        ));
        tblPhieuNhap.getTableHeader().setReorderingAllowed(false);
        tblPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuNhapMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhieuNhap);
        if (tblPhieuNhap.getColumnModel().getColumnCount() > 0) {
            tblPhieuNhap.getColumnModel().getColumn(0).setMinWidth(0);
            tblPhieuNhap.getColumnModel().getColumn(0).setMaxWidth(0);
            tblPhieuNhap.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        tblPhieuNhapChiTiet.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblPhieuNhapChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id phiếu nhập", "Id nguyên liệu", "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng nhập", "Đơn vị tính", "Đơn giá", "Thành tiền"
            }
        ));
        tblPhieuNhapChiTiet.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tblPhieuNhapChiTiet);
        if (tblPhieuNhapChiTiet.getColumnModel().getColumnCount() > 0) {
            tblPhieuNhapChiTiet.getColumnModel().getColumn(0).setMinWidth(0);
            tblPhieuNhapChiTiet.getColumnModel().getColumn(0).setMaxWidth(0);
            tblPhieuNhapChiTiet.getColumnModel().getColumn(1).setMinWidth(0);
            tblPhieuNhapChiTiet.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(btnExport))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtTimKiemPhieuNhap)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTimKiemPhieuNhap)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoHuyPhieuNhap)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoPhieuNhapTam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoHoanThanhPhieuNhap)
                                .addGap(2, 2, 2))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnImport)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 292, Short.MAX_VALUE)
                                .addComponent(btnCapNhatPhieuNhap)
                                .addGap(7, 7, 7)
                                .addComponent(btnHoanThanhPhieuNhap)))))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemPhieuNhap)
                    .addComponent(rdoHuyPhieuNhap)
                    .addComponent(rdoPhieuNhapTam)
                    .addComponent(rdoHoanThanhPhieuNhap)
                    .addComponent(jLabel32)
                    .addComponent(btnTimKiemPhieuNhap)
                    .addComponent(jRadioButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnImport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCapNhatPhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHoanThanhPhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel18.setBackground(new java.awt.Color(228, 212, 189));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel33.setText("Nguyên liệu:");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel34.setText("Nhà cung cấp:");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel35.setText("Nhân viên:");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel36.setText("Ngày nhập:");

        cboNguyenLieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNguyenLieuNhapActionPerformed(evt);
            }
        });

        btnTaoPhieuNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTaoPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add_20px.png"))); // NOI18N
        btnTaoPhieuNhap.setText("Tạo phiếu nhập");
        btnTaoPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoPhieuNhapActionPerformed(evt);
            }
        });

        btnHuyPhieuNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHuyPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_remove_30px.png"))); // NOI18N
        btnHuyPhieuNhap.setText("Hủy");
        btnHuyPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyPhieuNhapActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel39.setText("Mã phiếu nhập:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Phiếu nhập");

        tblNguyenLieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng nhập", "Đơn vị tính", "Đơn giá", "Gỡ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguyenLieu.getTableHeader().setReorderingAllowed(false);
        tblNguyenLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguyenLieuMouseClicked(evt);
            }
        });
        tblNguyenLieu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblNguyenLieuKeyReleased(evt);
            }
        });
        jScrollPane9.setViewportView(tblNguyenLieu);
        if (tblNguyenLieu.getColumnModel().getColumnCount() > 0) {
            tblNguyenLieu.getColumnModel().getColumn(0).setMinWidth(0);
            tblNguyenLieu.getColumnModel().getColumn(0).setMaxWidth(0);
            tblNguyenLieu.getColumnModel().getColumn(6).setMinWidth(30);
            tblNguyenLieu.getColumnModel().getColumn(6).setMaxWidth(30);
        }

        dateNgayNhap.setDateFormatString("dd-MM-yyyy");

        cboChiNhanhNhap.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboChiNhanhNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChiNhanhNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChiNhanhNhapActionPerformed(evt);
            }
        });

        lblCNNhap.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCNNhap.setText("Chi nhánh");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnHuyPhieuNhap)
                        .addGap(18, 18, 18)
                        .addComponent(btnTaoPhieuNhap))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboNhaCungCapNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addGap(18, 18, 18)
                                .addComponent(txtMaPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel34)
                            .addComponent(jLabel36)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(dateNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(cboNguyenLieuNhap, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(82, 82, 82)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(cboNhanVienNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(lblCNNhap)
                                .addGap(18, 18, 18)
                                .addComponent(cboChiNhanhNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboChiNhanhNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCNNhap))
                .addGap(4, 4, 4)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNhaCungCapNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboNhanVienNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboNguyenLieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(147, 147, 147))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phiếu nhập hàng", jPanel2);

        jPanel9.setBackground(new java.awt.Color(228, 212, 189));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách phiếu trả", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        tblChiTietPhieuTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblChiTietPhieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "IdPhieuTra", "IdNguyenLieu", "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng trả", "Đơn vị tính", "Đơn giá", "Thành tiền", "Lý do"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChiTietPhieuTra.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblChiTietPhieuTra);
        if (tblChiTietPhieuTra.getColumnModel().getColumnCount() > 0) {
            tblChiTietPhieuTra.getColumnModel().getColumn(0).setMinWidth(0);
            tblChiTietPhieuTra.getColumnModel().getColumn(0).setMaxWidth(0);
            tblChiTietPhieuTra.getColumnModel().getColumn(1).setMinWidth(0);
            tblChiTietPhieuTra.getColumnModel().getColumn(1).setMaxWidth(0);
            tblChiTietPhieuTra.getColumnModel().getColumn(2).setResizable(false);
            tblChiTietPhieuTra.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setText("Nhập mã phiếu:");

        buttonGroup1.add(rdoHuyPhieuTra);
        rdoHuyPhieuTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoHuyPhieuTra.setText("Đã hủy");
        rdoHuyPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHuyPhieuTraActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoHoanThanhPhieuTra);
        rdoHoanThanhPhieuTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoHoanThanhPhieuTra.setText("Đã hoàn thành");
        rdoHoanThanhPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHoanThanhPhieuTraActionPerformed(evt);
            }
        });

        btnTimKiemPhieuTra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiemPhieuTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search_20px.png"))); // NOI18N
        btnTimKiemPhieuTra.setText("Tìm kiếm");
        btnTimKiemPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemPhieuTraActionPerformed(evt);
            }
        });

        btnHoanThanhPhieuTra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHoanThanhPhieuTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_ok_30px.png"))); // NOI18N
        btnHoanThanhPhieuTra.setText("Hoàn thành");
        btnHoanThanhPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoanThanhPhieuTraActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoPhieuTraTam);
        rdoPhieuTraTam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoPhieuTraTam.setText("Phiếu tạm");
        rdoPhieuTraTam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPhieuTraTamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoTatCaPhieuTra);
        rdoTatCaPhieuTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoTatCaPhieuTra.setText("Tất cả");
        rdoTatCaPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTatCaPhieuTraActionPerformed(evt);
            }
        });

        btnCapNhatPhieuTra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhatPhieuTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_sync_30px.png"))); // NOI18N
        btnCapNhatPhieuTra.setText("Cập nhật phiếu trả");
        btnCapNhatPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatPhieuTraActionPerformed(evt);
            }
        });

        btnExport1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnExport1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_microsoft_excel_30px.png"))); // NOI18N
        btnExport1.setText("Xuất excel");
        btnExport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExport1ActionPerformed(evt);
            }
        });

        btnImport1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnImport1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_microsoft_excel_30px.png"))); // NOI18N
        btnImport1.setText("Mở excel");
        btnImport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImport1ActionPerformed(evt);
            }
        });

        tblPhieuTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblPhieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã phiếu trả", "Mã nhà cung cấp", "Nhà cung cấp", "Mã nhân viên", "Tên nhân viên", "Ngày trả", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieuTra.getTableHeader().setReorderingAllowed(false);
        tblPhieuTra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuTraMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblPhieuTra);
        if (tblPhieuTra.getColumnModel().getColumnCount() > 0) {
            tblPhieuTra.getColumnModel().getColumn(0).setMinWidth(0);
            tblPhieuTra.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiemPhieuTra)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTimKiemPhieuTra)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoTatCaPhieuTra)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoHuyPhieuTra)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoPhieuTraTam))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(btnExport1)
                                .addGap(18, 18, 18)
                                .addComponent(btnImport1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 325, Short.MAX_VALUE)
                                .addComponent(btnCapNhatPhieuTra)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rdoHoanThanhPhieuTra)
                            .addComponent(btnHoanThanhPhieuTra)))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoHuyPhieuTra)
                    .addComponent(rdoHoanThanhPhieuTra)
                    .addComponent(rdoPhieuTraTam)
                    .addComponent(rdoTatCaPhieuTra)
                    .addComponent(jLabel41)
                    .addComponent(txtTimKiemPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemPhieuTra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnCapNhatPhieuTra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHoanThanhPhieuTra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImport1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExport1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(228, 212, 189));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phiếu trả", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("Nguyên liệu:");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setText("Nhà cung cấp:");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("Nhân viên:");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setText("Ngày trả:");

        cboNguyenLieuTra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNguyenLieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNguyenLieuTraActionPerformed(evt);
            }
        });

        cboNhaCungCapTra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboNhanVienTra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnTaoPhieuTra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTaoPhieuTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add_20px.png"))); // NOI18N
        btnTaoPhieuTra.setText("Tạo phiếu trả");
        btnTaoPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoPhieuTraActionPerformed(evt);
            }
        });

        btnHuyPhieuTra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHuyPhieuTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_remove_30px.png"))); // NOI18N
        btnHuyPhieuTra.setText("Hủy");
        btnHuyPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyPhieuTraActionPerformed(evt);
            }
        });

        dateNgayTra.setDateFormatString("dd-MM-yyyy");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel40.setText("Mã phiếu trả");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Phiếu trả");

        tblNguyenLieuTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblNguyenLieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdNguyenLieu", "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng trả", "Đơn vị tính", "Lý do", "Gỡ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguyenLieuTra.getTableHeader().setReorderingAllowed(false);
        tblNguyenLieuTra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguyenLieuTraMouseClicked(evt);
            }
        });
        tblNguyenLieuTra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblNguyenLieuTraKeyReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblNguyenLieuTra);
        if (tblNguyenLieuTra.getColumnModel().getColumnCount() > 0) {
            tblNguyenLieuTra.getColumnModel().getColumn(0).setMinWidth(0);
            tblNguyenLieuTra.getColumnModel().getColumn(0).setMaxWidth(0);
            tblNguyenLieuTra.getColumnModel().getColumn(1).setResizable(false);
            tblNguyenLieuTra.getColumnModel().getColumn(2).setResizable(false);
            tblNguyenLieuTra.getColumnModel().getColumn(6).setMinWidth(30);
            tblNguyenLieuTra.getColumnModel().getColumn(6).setMaxWidth(30);
        }

        lblCNTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCNTra.setText("Chi nhánh");

        cboChiNhanhTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboChiNhanhTra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChiNhanhTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChiNhanhTraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnHuyPhieuTra)
                        .addGap(18, 18, 18)
                        .addComponent(btnTaoPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addGap(9, 9, 9)
                                .addComponent(txtMaPhieuTra)
                                .addGap(64, 64, 64))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addComponent(cboNhaCungCapTra, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29)
                                    .addComponent(dateNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel26)
                                    .addComponent(cboNguyenLieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(65, 65, 65)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                                        .addComponent(cboNhanVienTra, 0, 175, Short.MAX_VALUE)
                                        .addGap(64, 64, 64))
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addContainerGap(174, Short.MAX_VALUE))))))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(lblCNTra)
                        .addGap(18, 18, 18)
                        .addComponent(cboChiNhanhTra, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboChiNhanhTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCNTra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNhaCungCapTra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboNhanVienTra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboNguyenLieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(135, 135, 135))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 825, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phiếu trả hàng", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 924, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 151, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void showChiTietByPhieuNhap(String idPN) {
        modelChiTietPhieuNhap = (DefaultTableModel) tblPhieuNhapChiTiet.getModel();
        modelChiTietPhieuNhap.setRowCount(0);
        PhieuNhapViewModel pn = phieuNhapSevice.getPhieuNhapById(idPN);
        Set<ChiTietPhieuNhapViewModel> chiTietView = phieuNhapSevice.getPhieuNhapByChiTietPhieuNhap(idPN);
        for (ChiTietPhieuNhapViewModel ctView : chiTietView) {
            modelChiTietPhieuNhap.addRow(ctView.getDataChiTietPhieuNhapView());
        }
    }

    private void showNguyenLieuByPhieuNhap(String idPN) {
        modelNguyenLieu = (DefaultTableModel) tblNguyenLieu.getModel();
        modelNguyenLieu.setRowCount(0);
        PhieuNhapViewModel pn = phieuNhapSevice.getPhieuNhapById(idPN);
        Set<ChiTietPhieuNhapViewModel> chiTietView = phieuNhapSevice.getPhieuNhapByChiTietPhieuNhap(idPN);
        for (ChiTietPhieuNhapViewModel ctView : chiTietView) {
            modelNguyenLieu.addRow(ctView.getDataNguyenLieuView());
        }
    }

    private void clearFormPhieuNhap() {
        txtMaPhieuNhap.setText("");
        cboNguyenLieuNhap.setSelectedIndex(0);
        cboNhanVienNhap.setSelectedIndex(0);
        cboNhaCungCapNhap.setSelectedIndex(0);
        dateNgayNhap.setDate(null);
        modelNguyenLieu.setRowCount(0);
        modelChiTietPhieuNhap.setRowCount(0);
    }
    private void cboNguyenLieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNguyenLieuNhapActionPerformed
        int count = 0;
//        comboNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNguyenLieu().toArray());
//        cboNguyenLieuNhap.setModel((DefaultComboBoxModel) comboNguyenLieu);
        if (cboNguyenLieuNhap.getItemCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Chưa có nguyên liệu");
        } else {
            NguyenLieuViewModel_Hoang nguyenLieu = (NguyenLieuViewModel_Hoang) comboNguyenLieu.getSelectedItem();
            for (int i = 0; i < tblNguyenLieu.getRowCount(); i++) {
                if (nguyenLieu.getId().equalsIgnoreCase(tblNguyenLieu.getValueAt(i, 0).toString())) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                modelNguyenLieu.addRow(new Object[]{nguyenLieu.getId(), nguyenLieu.getMa(), nguyenLieu.getTen(), 1, nguyenLieu.getDonVitinh(), 1});
            }
        }
    }//GEN-LAST:event_cboNguyenLieuNhapActionPerformed
    private boolean checkEmpty() {
        if (txtMaPhieuNhap.getText().isEmpty() || cboNhaCungCapNhap.getSelectedItem() == null || cboNguyenLieuNhap.getSelectedItem() == null || cboNhanVienNhap.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Dũ liệu trống");
            return false;
        } else if (tblNguyenLieu.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm nguyên liệu");
            return false;
        } else if (dateNgayNhap.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày tháng");
            return false;
        } else if (txtMaPhieuNhap.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Ký tự không hợp lệ");
            return false;
        } else if (txtMaPhieuNhap.getText().length() > 10) {
            JOptionPane.showMessageDialog(this, "Mã phiếu nhập không vượt quá 10 ký tự");
            return false;
        }
        return true;
    }

    private boolean checkEmptyPhieuTra() {
        int row = tblNguyenLieuTra.getSelectedRow();
        if (txtMaPhieuTra.getText().isEmpty() || cboNhaCungCapTra.getSelectedItem() == null || cboNguyenLieuTra.getSelectedItem() == null || cboNhanVienTra.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Dũ liệu trống");
            return false;
        } else if (tblNguyenLieuTra.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm nguyên liệu");
            return false;
        } else if (dateNgayTra.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày tháng");
            return false;
        } else if (txtMaPhieuTra.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Ký tự không hợp lệ");
            return false;
        } else if (txtMaPhieuTra.getText().length() > 10) {
            JOptionPane.showMessageDialog(this, "Mã phiếu nhập không vượt quá 10 ký tự");
            return false;
        } else if (tblNguyenLieuTra.getValueAt(row, 5).toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "null");
            return false;
        }
        return true;
    }

    public boolean checkMaPhieuTra(String maPT) {
        if (phieuTraService.getPhieuTraByMa(maPT) != null) {
            JOptionPane.showMessageDialog(this, "Mã phiếu trả đã tồn tại");
            return true;
        }
        return false;
    }

    public boolean checkMaPhieuNhap(String maPN) {
        if (phieuNhapSevice.getPhieuNhapByMa(maPN) != null) {
            JOptionPane.showMessageDialog(this, "Mã phiếu nhập đã tồn tại");
            return true;
        }
        return false;
    }
    private void btnTaoPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoPhieuNhapActionPerformed

        if (checkEmpty() && !checkMaPhieuNhap(txtMaPhieuNhap.getText())) {
            String idPhieuNhap = null;
            idPhieuNhap = phieuNhapSevice.insertPhieuNhap(txtMaPhieuNhap.getText(), ((NhaCungCapViewModel_Hoang) cboNhaCungCapNhap.getSelectedItem()).getId(), ((NhanVienViewModel_Hoang) cboNhanVienNhap.getSelectedItem()).getId(),
                    dateNgayNhap.getDate(), 1);
            for (int i = 0; i < tblNguyenLieu.getRowCount(); i++) {
                phieuNhapSevice.insertCTPhieuNhap(idPhieuNhap, tblNguyenLieu.getValueAt(i, 0).toString(), Float.parseFloat(tblNguyenLieu.getValueAt(i, 3).toString()), Float.parseFloat(tblNguyenLieu.getValueAt(i, 5).toString()));
            }

            JOptionPane.showMessageDialog(this, "Tạo phiếu nhập thành công");
            loadAll((((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()));
            clearFormPhieuNhap();
        }
    }//GEN-LAST:event_btnTaoPhieuNhapActionPerformed

    private void btnHuyPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyPhieuNhapActionPerformed
        int row = tblPhieuNhap.getSelectedRow();
//        PhieuNhapViewModel pnView = new PhieuNhapViewModel();
        lstPhieuNhap = phieuNhapSevice.getAllPhieuNhapByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId());
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu nhập");
        } else {
//            pnView = lstPhieuNhap.get(row);
            int chon = JOptionPane.showConfirmDialog(this, "Xác nhận hủy phiếu", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (chon == JOptionPane.YES_OPTION) {
                if (tblPhieuNhap.getValueAt(row, 7).toString().equalsIgnoreCase("Phiếu tạm")) {
                    JOptionPane.showMessageDialog(this, phieuNhapSevice.updateTrangThaiPhieuNhap(tblPhieuNhap.getValueAt(row, 1).toString(), 0));
                    loadAll((((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()));
                    rdoHuyPhieuNhap.setSelected(true);
                    loadHuyPhieuNhap(lstPhieuNhap);
                    clearFormPhieuNhap();
                } else {
                    JOptionPane.showMessageDialog(this, "Phiếu đã nhập không thể hủy");
                }
            }
        }

    }//GEN-LAST:event_btnHuyPhieuNhapActionPerformed

    private void btnCapNhatPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatPhieuNhapActionPerformed
        // TODO add your handling code here:
        if (checkEmpty()) {
            int row = tblPhieuNhap.getSelectedRow();
            if (tblPhieuNhap.getValueAt(row, 3) != null && tblPhieuNhap.getValueAt(row, 5) != null && tblPhieuNhap.getValueAt(row, 6) != null) {
                if (txtMaPhieuNhap.getText().equalsIgnoreCase(tblPhieuNhap.getValueAt(row, 1).toString())) {
                    phieuNhapSevice.updatePhieuNhap(tblPhieuNhap.getValueAt(row, 0).toString(), tblPhieuNhap.getValueAt(row, 1).toString(), ((NhaCungCapViewModel_Hoang) cboNhaCungCapNhap.getSelectedItem()).getId(), ((NhanVienViewModel_Hoang) cboNhanVienNhap.getSelectedItem()).getId(),
                            dateNgayNhap.getDate());
//            phieuNhapSevice.deleteChiTietPnbyidPn(tblPhieuNhap.getValueAt(row, 0).toString());
//            for (int i = 0; i < tblNguyenLieu.getRowCount(); i++) {
//                phieuNhapSevice.insertCTPhieuNhap(tblPhieuNhap.getValueAt(row, 0).toString(), tblNguyenLieu.getValueAt(i, 0).toString(), Float.parseFloat(tblNguyenLieu.getValueAt(i, 3).toString()), Float.parseFloat(tblNguyenLieu.getValueAt(row, 5).toString()));
//            }
                    clearFormPhieuNhap();
                    loadAll(((ChiNhanhViewModel_Hoang) cboChiNhanhNhap.getSelectedItem()).getId());
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Không được sửa mã phiếu nhập");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Dữ liệu trống");
            }
        }

    }//GEN-LAST:event_btnCapNhatPhieuNhapActionPerformed

    private void cboNguyenLieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNguyenLieuTraActionPerformed
        int count = 0;
        if (cboNguyenLieuTra.getItemCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Chưa có nguyên liệu");
        } else {
            NguyenLieuViewModel_Hoang nguyenLieu = (NguyenLieuViewModel_Hoang) comboNguyenLieuTra.getSelectedItem();
            for (int i = 0; i < tblNguyenLieuTra.getRowCount(); i++) {
                if (nguyenLieu.getId().equalsIgnoreCase(tblNguyenLieuTra.getValueAt(i, 0).toString())) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                modelNguyenLieuTra.addRow(new Object[]{nguyenLieu.getId(), nguyenLieu.getMa(), nguyenLieu.getTen(), 1, nguyenLieu.getDonVitinh(), "Nhập lý do"});
            }
        }
    }//GEN-LAST:event_cboNguyenLieuTraActionPerformed

    private void btnHuyPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyPhieuTraActionPerformed
        int row = tblPhieuTra.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu trả");
        } else {
            if (tblPhieuTra.getValueAt(row, 7).toString().equalsIgnoreCase("Phiếu tạm")) {
                int chon = JOptionPane.showConfirmDialog(this, "Xác nhận hủy phiếu", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (chon == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(this, phieuTraService.updateTrangThaiPhieuTra(tblPhieuTra.getValueAt(row, 1).toString(), 0));
                    lstPhieuTra = phieuTraService.getAllPhieuTraByChiNhanh(((ChiNhanhViewModel_Hoang) cboChiNhanhTra.getSelectedItem()).getId());
                    loadAll(((ChiNhanhViewModel_Hoang) cboChiNhanhTra.getSelectedItem()).getId());
                    loadTableHuyPhieuTra(lstPhieuTra);
                    rdoHuyPhieuTra.setSelected(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không thể hủy");
            }
        }
    }//GEN-LAST:event_btnHuyPhieuTraActionPerformed
    private void showChiTietPhieuTraByPhieuTra(String idPhieuTra) {
        modelChiTietPhieuTra = (DefaultTableModel) tblChiTietPhieuTra.getModel();
        modelChiTietPhieuTra.setRowCount(0);
        PhieuTraViewModel ptView = phieuTraService.getPhieuTraByID(idPhieuTra);
        Set<ChiTietPhieuTraViewModel> chiTietView = phieuTraService.getPhieuTraByChiTietPhieuTra(idPhieuTra);
        for (ChiTietPhieuTraViewModel ctView : chiTietView) {
            modelChiTietPhieuTra.addRow(ctView.getChiTietPhieuTrahangView());
        }
    }
    private void btnCapNhatPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatPhieuTraActionPerformed
        // TODO add your handling code here:
        if (checkEmptyPhieuTra()) {
            int row = tblPhieuTra.getSelectedRow();
            if (tblPhieuTra.getValueAt(row, 2) != null && tblPhieuTra.getValueAt(row, 3) != null && tblPhieuTra.getValueAt(row, 5) != null && tblPhieuTra.getValueAt(row, 6) != null) {
                phieuTraService.updatePhieuTra(tblPhieuTra.getValueAt(row, 0).toString(), tblPhieuTra.getValueAt(row, 1).toString(), ((NhaCungCapViewModel_Hoang) cboNhaCungCapTra.getSelectedItem()).getId(), ((NhanVienViewModel_Hoang) cboNhanVienTra.getSelectedItem()).getId(),
                        dateNgayTra.getDate());
                phieuTraService.deleteChiTietPnbyidPT(tblPhieuTra.getValueAt(row, 0).toString());
                for (int i = 0; i < tblNguyenLieuTra.getRowCount(); i++) {
                    phieuTraService.insertCTPhieuTra(tblPhieuTra.getValueAt(row, 0).toString(), tblNguyenLieuTra.getValueAt(i, 0).toString(), Float.parseFloat(tblNguyenLieuTra.getValueAt(i, 3).toString()), tblNguyenLieuTra.getValueAt(row, 5).toString());
                }
                clearPhieuTra();
                loadAll(((ChiNhanhViewModel_Hoang) cboChiNhanhNhap.getSelectedItem()).getId());
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Dữ liệu trống");
            }
        }
    }//GEN-LAST:event_btnCapNhatPhieuTraActionPerformed
    private void loadTableHoaDonChiTiet(List<ChitietHoaDonViewModel> lstChiTietHD) {
        modelHDCT = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        modelHDCT.setRowCount(0);
        for (ChitietHoaDonViewModel x : lstChiTietHD) {
            modelHDCT.addRow(x.getDataHoaDonChiTietView());
        }
    }
    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        int row = tblHoaDon.getSelectedRow();
        lstChiTietHD = hoaDonChiTietService.getHoaDonChiTietByMaHoaDon(tblHoaDon.getValueAt(row, 0).toString());
        loadTableHoaDonChiTiet(lstChiTietHD);
    }//GEN-LAST:event_tblHoaDonMouseClicked
    private void loadHuyPhieuNhap(Set<PhieuNhapViewModel> lstPhieuNhap) {
        modelPhieuNhap = (DefaultTableModel) tblPhieuNhap.getModel();
        modelPhieuNhap.setRowCount(0);
        for (PhieuNhapViewModel x : lstPhieuNhap) {
            if (x.getTrangThai() == 0) {
                modelPhieuNhap.addRow(x.getDataPhieuNhapView());
            }
        }
    }
    private void rdoHuyPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHuyPhieuNhapActionPerformed
        loadHuyPhieuNhap(lstPhieuNhap);
        clearFormPhieuNhap();
    }//GEN-LAST:event_rdoHuyPhieuNhapActionPerformed
    private void loadDaHoanThanh(Set<PhieuNhapViewModel> lstPhieuNhap) {
        modelPhieuNhap = (DefaultTableModel) tblPhieuNhap.getModel();
        modelPhieuNhap.setRowCount(0);
        for (PhieuNhapViewModel x : lstPhieuNhap) {
            if (x.getTrangThai() == 3) {
                modelPhieuNhap.addRow(x.getDataPhieuNhapView());
            }
        }
    }
    private void rdoHoanThanhPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHoanThanhPhieuNhapActionPerformed
        loadDaHoanThanh(lstPhieuNhap);
        clearFormPhieuNhap();
    }//GEN-LAST:event_rdoHoanThanhPhieuNhapActionPerformed
    private void loadPhieuTam(Set<PhieuNhapViewModel> lstPhieuNhap) {
        modelPhieuNhap = (DefaultTableModel) tblPhieuNhap.getModel();
        modelPhieuNhap.setRowCount(0);
        for (PhieuNhapViewModel x : lstPhieuNhap) {
            if (x.getTrangThai() == 1) {
                modelPhieuNhap.addRow(x.getDataPhieuNhapView());
            }
        }
    }
    private void rdoPhieuNhapTamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPhieuNhapTamActionPerformed
        loadPhieuTam(lstPhieuNhap);
        clearFormPhieuNhap();
    }//GEN-LAST:event_rdoPhieuNhapTamActionPerformed

    private void btnTimKiemPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemPhieuNhapActionPerformed
        if (txtTimKiemPhieuNhap.getText().isEmpty()) {
            loadTablePhieuNhap(phieuNhapSevice.getAllPhieuNhapByChiNhanh(((ChiNhanhViewModel_Hoang) cboChiNhanhNhap.getSelectedItem()).getId()));
        } else {
            Set<PhieuNhapViewModel> lstSearch = phieuNhapSevice.searchPhieuNhap(txtTimKiemPhieuNhap.getText());
            if (lstSearch.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tồn tại phiếu nhập");
            } else {
                loadTablePhieuNhap(lstSearch);
            }
        }
    }//GEN-LAST:event_btnTimKiemPhieuNhapActionPerformed
    private void loadTablePhieuTraTam(Set<PhieuTraViewModel> lstPhieuTra) {
        modelPhieuTra = (DefaultTableModel) tblPhieuTra.getModel();
        modelPhieuTra.setRowCount(0);
        for (PhieuTraViewModel x : lstPhieuTra) {
            if (x.getTrangThai() == 1) {
                modelPhieuTra.addRow(x.getPhieuTrahangView());
            }
        }
    }
    private void rdoPhieuTraTamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPhieuTraTamActionPerformed
        loadTablePhieuTraTam(lstPhieuTra);
        clearPhieuTra();
    }//GEN-LAST:event_rdoPhieuTraTamActionPerformed

    private void btnHoanThanhPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoanThanhPhieuNhapActionPerformed
        int row = tblPhieuNhap.getSelectedRow();
        lstPhieuNhap = phieuNhapSevice.getAllPhieuNhapByChiNhanh(((ChiNhanhViewModel_Hoang) cboChiNhanhNhap.getSelectedItem()).getId());
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu nhập");
        } else {
            int chon = JOptionPane.showConfirmDialog(this, "Xác nhận hoàn thành phiếu", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (chon == JOptionPane.YES_OPTION) {
                if (tblPhieuNhap.getValueAt(row, 7).toString().equalsIgnoreCase("Phiếu tạm")) {
                    JOptionPane.showMessageDialog(this, phieuNhapSevice.updateTrangThaiPhieuNhap(tblPhieuNhap.getValueAt(row, 1).toString(), 3));
                    for (int i = 0; i < tblNguyenLieu.getRowCount(); i++) {
                        phieuNhapSevice.updateSoluongNguyenLieu(tblNguyenLieu.getValueAt(i, 0).toString(), Float.parseFloat(tblNguyenLieu.getValueAt(i, 3).toString()));
                    }
                    JOptionPane.showMessageDialog(this, "Đã hoàn thành phiếu và cập nhật số lượng trong kho");
                    loadAll(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId());
                    loadDaHoanThanh(lstPhieuNhap);
                    rdoHoanThanhPhieuNhap.setSelected(true);
                    clearFormPhieuNhap();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể hoàn thành phiếu");
                }
            }
        }
    }//GEN-LAST:event_btnHoanThanhPhieuNhapActionPerformed

    private void btnTimKiemPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemPhieuTraActionPerformed
        if (txtTimKiemPhieuTra.getText().isEmpty()) {
            loadTablePhieuTra(phieuTraService.getAllPhieuTraByChiNhanh(((ChiNhanhViewModel_Hoang) cboChiNhanhTra.getSelectedItem()).getId()));
        } else {
            Set<PhieuTraViewModel> lstSearchPhieuTra = phieuTraService.searchPhieuTra(txtTimKiemPhieuTra.getText());
            if (lstSearchPhieuTra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tồn tại mã phiếu này");
            } else {
                loadTablePhieuTra(lstSearchPhieuTra);
            }
        }
    }//GEN-LAST:event_btnTimKiemPhieuTraActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        loadTablePhieuNhap(lstPhieuNhap);
        clearFormPhieuNhap();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void rdoTatCaPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTatCaPhieuTraActionPerformed
        loadTablePhieuTra(lstPhieuTra);
        clearPhieuTra();
    }//GEN-LAST:event_rdoTatCaPhieuTraActionPerformed
    private void loadTableHuyPhieuTra(Set<PhieuTraViewModel> lstPhieuTra) {
        modelPhieuTra = (DefaultTableModel) tblPhieuTra.getModel();
        modelPhieuTra.setRowCount(0);
        for (PhieuTraViewModel x : lstPhieuTra) {
            if (x.getTrangThai() == 0) {
                modelPhieuTra.addRow(x.getPhieuTrahangView());
            }
        }
    }
    private void rdoHuyPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHuyPhieuTraActionPerformed
        loadTableHuyPhieuTra(lstPhieuTra);
    }//GEN-LAST:event_rdoHuyPhieuTraActionPerformed
    private void loadTablePhieuTraHoanThanh(Set<PhieuTraViewModel> lstPhieuTra) {
        modelPhieuTra = (DefaultTableModel) tblPhieuTra.getModel();
        modelPhieuTra.setRowCount(0);
        for (PhieuTraViewModel x : lstPhieuTra) {
            if (x.getTrangThai() == 3) {
                modelPhieuTra.addRow(x.getPhieuTrahangView());
            }
        }
    }
    private void rdoHoanThanhPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHoanThanhPhieuTraActionPerformed
        loadTablePhieuTraHoanThanh(lstPhieuTra);    }//GEN-LAST:event_rdoHoanThanhPhieuTraActionPerformed
    private void clearPhieuTra() {
        txtMaPhieuTra.setText("");
        cboNhaCungCapTra.setSelectedIndex(0);
        cboNhanVienTra.setSelectedIndex(0);
        cboNguyenLieuTra.setSelectedIndex(0);
        dateNgayTra.setDate(null);
        modelNguyenLieuTra.setRowCount(0);
        modelChiTietPhieuTra.setRowCount(0);
    }
    private void btnHoanThanhPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoanThanhPhieuTraActionPerformed
        int row = tblPhieuTra.getSelectedRow();
        lstPhieuTra = phieuTraService.getAllPhieuTraByChiNhanh(((ChiNhanhViewModel_Hoang) cboChiNhanhTra.getSelectedItem()).getId());
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu trả");
        } else {
            if (tblPhieuTra.getValueAt(row, 7).toString().equalsIgnoreCase("Phiếu tạm")) {
                int chon = JOptionPane.showConfirmDialog(this, "Xác nhận hoàn thành phiếu", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (chon == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(this, phieuTraService.updateTrangThaiPhieuTra(tblPhieuTra.getValueAt(row, 1).toString(), 3));
                    for (int i = 0; i < tblNguyenLieuTra.getRowCount(); i++) {
                        phieuTraService.updateSoluongNguyenLieuTra(tblNguyenLieuTra.getValueAt(i, 0).toString(), Float.parseFloat(tblNguyenLieuTra.getValueAt(i, 3).toString()));
                    }
                    loadAll(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId());
                    loadTablePhieuTraHoanThanh(lstPhieuTra);
                    rdoHoanThanhPhieuTra.setSelected(true);
                    JOptionPane.showMessageDialog(this, "Đã hoàn thành phiếu và cập nhật số lượng trong kho");

                    clearPhieuTra();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không thể hoàn thành");
            }
        }
    }//GEN-LAST:event_btnHoanThanhPhieuTraActionPerformed

    private void btnLocHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocHoaDonActionPerformed
        // TODO add your handling code here:
        if (dateFrom.getDate() == null || dateTo.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày tháng năm");
        } else {
            List<HoaDonViewModel> lstSearchHoaDon = hoaDonService.locHoaDon(dateFrom.getDate(), dateTo.getDate());
            loadTableHoaDon(lstSearchHoaDon);
        }
    }//GEN-LAST:event_btnLocHoaDonActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:

        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
        JFileChooser excelFileChooser = new JFileChooser("C:\\Users\\ASUS\\Desktop\\TestExcel");
        excelFileChooser.setDialogTitle("Save As");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChosser = excelFileChooser.showSaveDialog(null);
        if (excelChosser == JFileChooser.APPROVE_OPTION) {

            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");
                for (int i = 0; i < modelPhieuNhap.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j < modelPhieuNhap.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(modelPhieuNhap.getValueAt(i, j).toString());
                    }
                }
                excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
                JOptionPane.showMessageDialog(this, "Exported");
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {
            } finally {
                try {
                    if (excelBOU != null) {
                        excelBOU.close();
                    }
                    if (excelFOU != null) {
                        excelFOU.close();
                    }
                    if (excelJTableExporter != null) {
                        excelJTableExporter.close();
                    }
                } catch (IOException ex) {
                }
            }

        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
        modelPhieuNhap.setRowCount(0);
        File exelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;

        String defauCurrentDirectoryPath = "C:\\Users\\ASUS\\Desktop\\TestExcel";
        JFileChooser exportFlieChooser = new JFileChooser(defauCurrentDirectoryPath);

        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        exportFlieChooser.setFileFilter(fnef);
        exportFlieChooser.setDialogTitle("Select Excel file");
        int excelChooser = exportFlieChooser.showOpenDialog(null);

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                exelFile = exportFlieChooser.getSelectedFile();
                excelFIS = new FileInputStream(exelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);

                for (int row = 0; row < excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);

                    XSSFCell excelMaPhieu = excelRow.getCell(0);
                    XSSFCell excelMaNguyenLieu = excelRow.getCell(1);
                    XSSFCell excelTenNguyenLieu = excelRow.getCell(2);
                    XSSFCell excelMaNcc = excelRow.getCell(3);
                    XSSFCell excelTenNCC = excelRow.getCell(4);
                    XSSFCell excelMaNV = excelRow.getCell(5);
                    XSSFCell excelTenNV = excelRow.getCell(6);
                    XSSFCell excelNgayNhap = excelRow.getCell(7);
                    XSSFCell excelSoLuong = excelRow.getCell(8);
                    XSSFCell excelDonGia = excelRow.getCell(9);
                    XSSFCell excelThanhTien = excelRow.getCell(10);
                    XSSFCell excelTrangThai = excelRow.getCell(11);
                    modelPhieuNhap.addRow(new Object[]{excelMaPhieu, excelMaNguyenLieu, excelTenNguyenLieu, excelMaNcc, excelTenNCC, excelMaNV, excelTenNV,
                        excelNgayNhap, excelSoLuong, excelDonGia, excelThanhTien, excelTrangThai});
                }
                JOptionPane.showMessageDialog(null, "Thành công");
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } finally {
                try {
                    if (excelFIS != null) {
                        excelFIS.close();
                    }
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelJTableImport != null) {
                        excelJTableImport.close();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        }
    }//GEN-LAST:event_btnImportActionPerformed

    private void btnExport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExport1ActionPerformed
        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
        JFileChooser excelFileChooser = new JFileChooser("C:\\Users\\ASUS\\Desktop\\TestExcel");
        excelFileChooser.setDialogTitle("Save As");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChosser = excelFileChooser.showSaveDialog(null);
        if (excelChosser == JFileChooser.APPROVE_OPTION) {

            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");
                for (int i = 0; i < modelPhieuTra.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j < modelPhieuTra.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(modelPhieuTra.getValueAt(i, j).toString());
                    }
                }
                excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
                JOptionPane.showMessageDialog(this, "Exported");
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {
            } finally {
                try {
                    if (excelBOU != null) {
                        excelBOU.close();
                    }
                    if (excelFOU != null) {
                        excelFOU.close();
                    }
                    if (excelJTableExporter != null) {
                        excelJTableExporter.close();
                    }
                } catch (IOException ex) {
                }
            }

        }
    }//GEN-LAST:event_btnExport1ActionPerformed

    private void btnImport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImport1ActionPerformed
        // TODO add your handling code here:
        modelPhieuTra.setRowCount(0);
        File exelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;

        String defauCurrentDirectoryPath = "C:\\Users\\ASUS\\Desktop\\TestExcel";
        JFileChooser exportFlieChooser = new JFileChooser(defauCurrentDirectoryPath);

        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        exportFlieChooser.setFileFilter(fnef);
        exportFlieChooser.setDialogTitle("Select Excel file");
        int excelChooser = exportFlieChooser.showOpenDialog(null);

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                exelFile = exportFlieChooser.getSelectedFile();
                excelFIS = new FileInputStream(exelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);

                for (int row = 0; row < excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);

                    XSSFCell excelMaPhieu = excelRow.getCell(0);
                    XSSFCell excelMaNguyenLieu = excelRow.getCell(1);
                    XSSFCell excelTenNguyenLieu = excelRow.getCell(2);
                    XSSFCell excelMaNcc = excelRow.getCell(3);
                    XSSFCell excelTenNCC = excelRow.getCell(4);
                    XSSFCell excelMaNV = excelRow.getCell(5);
                    XSSFCell excelTenNV = excelRow.getCell(6);
                    XSSFCell excelNgayNhap = excelRow.getCell(7);
                    XSSFCell excelSoLuong = excelRow.getCell(8);
                    XSSFCell excelLyDo = excelRow.getCell(9);
                    XSSFCell excelTrangThai = excelRow.getCell(11);
                    modelPhieuTra.addRow(new Object[]{excelMaPhieu, excelMaNguyenLieu, excelTenNguyenLieu, excelMaNcc, excelTenNCC, excelMaNV, excelTenNV,
                        excelNgayNhap, excelSoLuong, excelLyDo, excelTrangThai});
                }
                JOptionPane.showMessageDialog(null, "Thành công");
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } finally {
                try {
                    if (excelFIS != null) {
                        excelFIS.close();
                    }
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelJTableImport != null) {
                        excelJTableImport.close();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        }
    }//GEN-LAST:event_btnImport1ActionPerformed

    private void btnTaoPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoPhieuTraActionPerformed
        // TODO add your handling code here:
        if (checkEmptyPhieuTra() && !checkMaPhieuTra(txtMaPhieuTra.getText())) {
            String idPhieuTra = null;
            idPhieuTra = phieuTraService.insertPhieuTra(txtMaPhieuTra.getText(), ((NhaCungCapViewModel_Hoang) cboNhaCungCapTra.getSelectedItem()).getId(), ((NhanVienViewModel_Hoang) cboNhanVienTra.getSelectedItem()).getId(),
                    dateNgayTra.getDate(), 1);
            for (int i = 0; i < tblNguyenLieuTra.getRowCount(); i++) {
                phieuTraService.insertCTPhieuTra(idPhieuTra, tblNguyenLieuTra.getValueAt(i, 0).toString(), Float.parseFloat(tblNguyenLieuTra.getValueAt(i, 3).toString()), tblNguyenLieuTra.getValueAt(i, 5).toString());
            }
            JOptionPane.showMessageDialog(this, "Thêm phiếu trả thành công");
            loadAll(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId());
            clearPhieuTra();
        }
    }//GEN-LAST:event_btnTaoPhieuTraActionPerformed
    private void fillDataPhieuNhap(int index) {
        int row = tblPhieuNhap.getSelectedRow();
        if (tblPhieuNhap.getRowCount() >= 0) {
            txtMaPhieuNhap.setText((String) tblPhieuNhap.getValueAt(index, 1).toString());
            for (int i = 0; i < phieuNhapSevice.getAllNhaCungCap().size(); i++) {
                if (phieuNhapSevice.getAllNhaCungCap().get(i).getMa().equals(tblPhieuNhap.getValueAt(index, 2))) {
                    cboNhaCungCapNhap.setSelectedIndex(i);
                }
            }
            for (int i = 0; i < phieuNhapSevice.getAllNhaCungCap().size(); i++) {
                if (phieuNhapSevice.getAllNhaCungCap().get(i).getTen().equals(tblPhieuNhap.getValueAt(index, 3))) {
                    cboNhaCungCapNhap.setSelectedIndex(i);
                }
            }
            for (int i = 0; i < phieuNhapSevice.getAllNhanVien().size(); i++) {
                if (phieuNhapSevice.getAllNhanVien().get(i).getMa().equals(tblPhieuNhap.getValueAt(index, 4))) {
                    cboNhanVienNhap.setSelectedIndex(i);
                }
            }
            for (int i = 0; i < phieuNhapSevice.getAllNhanVien().size(); i++) {
                if (phieuNhapSevice.getAllNhanVien().get(i).getHoTen().equals(tblPhieuNhap.getValueAt(index, 5))) {
                    cboNhanVienNhap.setSelectedIndex(i);
                }
            }
            dateNgayNhap.setDate((Date) tblPhieuNhap.getValueAt(index, 6));
//            try {
//                String dates = tblPhieuNhap.getValueAt(index, 6).toString();
//                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dates);
//                dateNgayNhap.setDate(date);
//            } catch (ParseException ex) {
//            }
        }
    }

    private void showNguyenLieuPhieuTraByPhieuTra(String idPt) {
        modelChiTietPhieuTra = (DefaultTableModel) tblNguyenLieuTra.getModel();
        modelChiTietPhieuTra.setRowCount(0);
        PhieuTraViewModel ptView = phieuTraService.getPhieuTraByID(idPt);
        Set<ChiTietPhieuTraViewModel> chiTietView = phieuTraService.getPhieuTraByChiTietPhieuTra(idPt);
        for (ChiTietPhieuTraViewModel ctView : chiTietView) {
            modelChiTietPhieuTra.addRow(ctView.getNguyenLieuByPhieuTrahangView());
        }
    }
    private void tblPhieuTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuTraMouseClicked
        int row = tblPhieuTra.getSelectedRow();
        showChiTietPhieuTraByPhieuTra(tblPhieuTra.getValueAt(row, 0).toString());
        showNguyenLieuPhieuTraByPhieuTra(tblPhieuTra.getValueAt(row, 0).toString());
        fillTablePhieuTra(row);
    }//GEN-LAST:event_tblPhieuTraMouseClicked

    private void tblPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuNhapMouseClicked
        // TODO add your handling code here:
        int index = tblPhieuNhap.getSelectedRow();
        showChiTietByPhieuNhap(tblPhieuNhap.getValueAt(index, 0).toString());
        showNguyenLieuByPhieuNhap(tblPhieuNhap.getValueAt(index, 0).toString());
        fillDataPhieuNhap(index);
    }//GEN-LAST:event_tblPhieuNhapMouseClicked

    private void tblNguyenLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguyenLieuMouseClicked
        int row = tblNguyenLieu.getSelectedRow();
        int column = tblNguyenLieu.getSelectedColumn();
        if (column == 6) {
            if (tblNguyenLieu.getSelectedColumn() == 6) {
                if (tblNguyenLieu.getValueAt(row, 6).equals(true)) {
                    modelNguyenLieu.removeRow(row);
                }
            }
        }
    }//GEN-LAST:event_tblNguyenLieuMouseClicked

    private void tblNguyenLieuTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguyenLieuTraMouseClicked
        // TODO add your handling code here:
        int row = tblNguyenLieuTra.getSelectedRow();
        int column = tblNguyenLieuTra.getSelectedColumn();
        if (column == 6) {
            if (tblNguyenLieuTra.getSelectedColumn() == 6) {
                if (tblNguyenLieuTra.getValueAt(row, 6).equals(true)) {
                    modelNguyenLieuTra.removeRow(row);
                }
            }
        }
    }//GEN-LAST:event_tblNguyenLieuTraMouseClicked
    private boolean checkSoluong() {
        int row = tblNguyenLieu.getSelectedRow();
        float soLuong;
        try {
            soLuong = Float.parseFloat(tblNguyenLieu.getValueAt(row, 3).toString());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập lại số lượng");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại số lượng");
            return false;
        }
        return true;
    }

    private boolean checkSoluongTra() {
        int row = tblNguyenLieuTra.getSelectedRow();
        float soLuong;
        try {
            soLuong = Float.parseFloat(tblNguyenLieuTra.getValueAt(row, 3).toString());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập lại số lượng");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại số lượng");
            return false;
        }
        return true;
    }

    private boolean checkDonGia() {
        int row = tblNguyenLieu.getSelectedRow();
        float donGia;
        try {
            donGia = Float.parseFloat(tblNguyenLieu.getValueAt(row, 5).toString());
            if (donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập lại đơn giá");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại đơn giá");
        }
        return false;
    }
    private void tblNguyenLieuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblNguyenLieuKeyReleased
        // TODO add your handling code here:
        int row = tblNguyenLieu.getSelectedRow();
        if (!checkSoluong()) {
            tblNguyenLieu.setValueAt(1, row, 3);

        } else if (!checkDonGia()) {
            tblNguyenLieu.setValueAt(1, row, 5);
        }
    }//GEN-LAST:event_tblNguyenLieuKeyReleased

    private void tblNguyenLieuTraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblNguyenLieuTraKeyReleased
        // TODO add your handling code here:
        int row = tblNguyenLieuTra.getSelectedRow();
        if (!checkSoluongTra()) {
            tblNguyenLieuTra.setValueAt(1, row, 3);
        }
    }//GEN-LAST:event_tblNguyenLieuTraKeyReleased

    private void cboChiNhanhNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChiNhanhNhapActionPerformed
        // TODO add your handling code here:
        modelNguyenLieu.setRowCount(0);
        txtMaPhieuNhap.setText("");
        dateNgayNhap.setDate(null);
        comboNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(
                phieuNhapSevice.getAllNguyenLieuByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()).toArray());
        cboNguyenLieuNhap.setModel((DefaultComboBoxModel) comboNguyenLieu);
        comboNhanVien = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhanVienByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()).toArray());
            cboNhanVienNhap.setModel((DefaultComboBoxModel) comboNhanVien);
        loadTablePhieuNhap(phieuNhapSevice.getAllPhieuNhapByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()));
    }//GEN-LAST:event_cboChiNhanhNhapActionPerformed

    private void cboChiNhanhTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChiNhanhTraActionPerformed
        // TODO add your handling code here:
        modelNguyenLieuTra.setRowCount(0);
        txtMaPhieuTra.setText("");
        dateNgayTra.setDate(null);
        comboNguyenLieuTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(
                phieuNhapSevice.getAllNguyenLieuByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()).toArray());
        cboNguyenLieuTra.setModel((DefaultComboBoxModel) comboNguyenLieuTra);
        comboNhanVienTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhanVienByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()).toArray());
            cboNhanVienTra.setModel((DefaultComboBoxModel) comboNhanVienTra);
        loadTablePhieuTra(phieuTraService.getAllPhieuTraByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()));
    }//GEN-LAST:event_cboChiNhanhTraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatPhieuNhap;
    private javax.swing.JButton btnCapNhatPhieuTra;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnExport1;
    private javax.swing.JButton btnHoanThanhPhieuNhap;
    private javax.swing.JButton btnHoanThanhPhieuTra;
    private javax.swing.JButton btnHuyPhieuNhap;
    private javax.swing.JButton btnHuyPhieuTra;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnImport1;
    private javax.swing.JButton btnLocHoaDon;
    private javax.swing.JButton btnTaoPhieuNhap;
    private javax.swing.JButton btnTaoPhieuTra;
    private javax.swing.JButton btnTimKiemPhieuNhap;
    private javax.swing.JButton btnTimKiemPhieuTra;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboChiNhanhNhap;
    private javax.swing.JComboBox<String> cboChiNhanhTra;
    private javax.swing.JComboBox<PhieuNhapViewModel> cboNguyenLieuNhap;
    private javax.swing.JComboBox<String> cboNguyenLieuTra;
    private javax.swing.JComboBox<NhaCungCap> cboNhaCungCapNhap;
    private javax.swing.JComboBox<String> cboNhaCungCapTra;
    private javax.swing.JComboBox<NhanVien> cboNhanVienNhap;
    private javax.swing.JComboBox<String> cboNhanVienTra;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateNgayNhap;
    private com.toedter.calendar.JDateChooser dateNgayTra;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCNNhap;
    private javax.swing.JLabel lblCNTra;
    private javax.swing.JRadioButton rdoHoanThanhPhieuNhap;
    private javax.swing.JRadioButton rdoHoanThanhPhieuTra;
    private javax.swing.JRadioButton rdoHuyPhieuNhap;
    private javax.swing.JRadioButton rdoHuyPhieuTra;
    private javax.swing.JRadioButton rdoPhieuNhapTam;
    private javax.swing.JRadioButton rdoPhieuTraTam;
    private javax.swing.JRadioButton rdoTatCaPhieuTra;
    private javax.swing.JTable tblChiTietPhieuTra;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTable tblNguyenLieu;
    private javax.swing.JTable tblNguyenLieuTra;
    private javax.swing.JTable tblPhieuNhap;
    private javax.swing.JTable tblPhieuNhapChiTiet;
    private javax.swing.JTable tblPhieuTra;
    private javax.swing.JTextField txtMaPhieuNhap;
    private javax.swing.JTextField txtMaPhieuTra;
    private javax.swing.JTextField txtTimKiemPhieuNhap;
    private javax.swing.JTextField txtTimKiemPhieuTra;
    // End of variables declaration//GEN-END:variables

}
