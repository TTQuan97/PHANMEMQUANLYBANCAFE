package service.implement;

import domainmodel.ChiNhanh;
import java.util.ArrayList;
import java.util.List;
import repository.KhuyenMaiRepo;
import service.IKhuyenMai;
import domainmodel.KhuyenMai;
import domainmodel.SanPham;
import java.math.BigDecimal;
import java.util.Date;
import viewmodel.ChiNhanhView;
import viewmodel.KhuyenMaiView;
import viewmodel.SanPhamViewModel;

/**
 *
 * @author duong
 */
public class KhuyenMaiService implements IKhuyenMai {

    KhuyenMaiRepo khuyenMaiRepo;

    public KhuyenMaiService() {
        khuyenMaiRepo = new KhuyenMaiRepo();
    }

    // get KM
    @Override
    public KhuyenMai getKMById(String id) {
        return khuyenMaiRepo.getKMById(id);
    }

    @Override
    public KhuyenMaiView getKhuyenMaiViewById(String id) {
        return toKhuyeMaiView(khuyenMaiRepo.getKMById(id));
    }

//    @Override
//    public KhuyenMaiView getKhuyenMaiByName(String tenKM) {
//        return findKhuyenMaiByName(tenKM).get(0);
//    }
    // READ
    @Override
    public List<KhuyenMaiView> getAllKhuyenMai() {
        return toDataView(khuyenMaiRepo.getAllKhuyenMai());
    }

    @Override
    public List<KhuyenMaiView> getAllKMByChiNhanh(ChiNhanh cn) {
        return toDataView(khuyenMaiRepo.getAllKMByChiNhanh(cn));
    }

    // Filter and searh all
    @Override
    public List<KhuyenMaiView> getAllKMByName(String tenKM) {
        return toDataView(khuyenMaiRepo.getAllKMByName(tenKM));
    }

//    List<KhuyenMaiView> getAllKMByDate(Date d1, Date d2);
    @Override
    public List<KhuyenMaiView> getAllKMByTrangThai(int trangThai) {
        return toDataView(khuyenMaiRepo.getAllKMByTrangThai(trangThai));
    }

    @Override
    public List<KhuyenMaiView> getAllKMByNameAndTrangThai(int trangThai, String tenKM) {
        return toDataView(khuyenMaiRepo.getAllKMByNameAndTrangThai(trangThai, tenKM));
    }

//    List<KhuyenMaiView> getAllNVByDateAndTrangThai(int trangThai, Date d1, Date d2);
    // Filter and search by chi nhanh
    @Override
    public List<KhuyenMaiView> getAllKMByChiNhanhAndName(ChiNhanh cn, String tenKM) {
        return toDataView(khuyenMaiRepo.getAllKMByChiNhanhAndName(cn, tenKM));
    }

//    List<KhuyenMaiView> getAllKMByChiNhanhAndDate(ChiNhanh cn, Date d1, Date d2);
    @Override
    public List<KhuyenMaiView> getAllKMByChiNhanhAndTrangThai(ChiNhanh cn, int trangThai) {
        return toDataView(khuyenMaiRepo.getAllKMByChiNhanhAndTrangThai(cn, trangThai));
    }

    @Override
    public List<KhuyenMaiView> getAllKMByChiNhanhAndTrangThaiAndName(ChiNhanh cn, int trangThai, String tenKM) {
        return toDataView(khuyenMaiRepo.getAllKMByChiNhanhAndTrangThaiAndName(cn, trangThai, tenKM));
    }

//    List<KhuyenMaiView> getAllKMByChiNhanhAndTrangThaiAndDate(ChiNhanh cn, int trangThai, Date d1, Date d2);
    // CRU
    @Override
    public String addKhuyenMai(KhuyenMai khuyenMai, List<SanPham> sanPhams) {
//            if (isKhuyenMaiExists(khuyenMai.getMa())) {
//                return "\"Ma khuyen mai\" da ton tai";
//            } else 
        if (khuyenMaiRepo.addKhuyenMai(khuyenMai, sanPhams)) {
            return "Them thanh cong!";
        } else {
            return "Them that bai!";
        }
    }

    @Override
    public String updateKhuyenMai(String id, KhuyenMai khuyenMai, List<SanPham> sanPhamAdds, List<SanPham> sanPhamDels) {
//        if(isKhuyenMaiExists(khuyenMai.getMa())) {
        if (khuyenMaiRepo.updateKhuyenMai(id, khuyenMai, sanPhamAdds, sanPhamDels)) {
            return "Cap nhat thanh cong!";
        } else {
            return "Cap nhat that bai!";
        }
//        } else {
//            return "\"Ma khuyen mai\" khong ton tai!";
//        }
    }

    @Override
    public String deleteKhuyenMai(String id) {
        if (khuyenMaiRepo.deleteKhuyenMai(id)) {
            return "Xoa thanh cong!";
        } else {
            return "Xoa that bai!";
        }
    }

    @Override
    public String deleteKM(String id, List<SanPham> sanPhamDels) {
        if (khuyenMaiRepo.deleteKM(id, sanPhamDels)) {
            return "Xoa thanh cong!";
        } else {
            return "Xoa that bai!";
        }
    }

    /// LOGIC
    // Cast KhuyenMai to KhuyenMaiView
    private List<KhuyenMaiView> toDataView(List<KhuyenMai> khuyenMais) {
        List<KhuyenMaiView> khuyenMaiViews = new ArrayList<>();
        if (!khuyenMais.isEmpty()) {
            for (KhuyenMai km : khuyenMais) {
                KhuyenMaiView kmv = toKhuyeMaiView(km);
                khuyenMaiViews.add(kmv);
            }
        }
        return khuyenMaiViews;
    }

    private KhuyenMaiView toKhuyeMaiView(KhuyenMai km) {
        return new KhuyenMaiView(km.getId(), km.getTen(), km.getNgayBatDau(),
                km.getNgayKetThuc(), km.getGiaTriChietKhau(), km.getTrangThai(), km.getMota());
    }

    // Check KhuyenMai Exists
    private Boolean isKhuyenMaiExists(String maKM) {
//        return !findKhuyenMaiByMa(maKM).isEmpty();
        return null;
    }

    // Validate Data Input
    @Override
    public String validateDataInput(Object[] data) {
        Date now = new Date();
        String message = "";
//        if (khuyenMai.getMa().isBlank()) {
//            message += "\"Ma khuyen mai\" khong de trong!\n";
//        }

        if (((String) data[0]).isBlank()) {
            message += "\"Ten khuyen mai\" khong de trong!\n";
        }

        if (((String) data[1]).isBlank()) {
            message += "\"Mo ta\" khong de trong!\n";
        }

        if ((Date) data[2] == null) {
            message += "\"Ngay bat dau\" khong de trong!\n";
        } else if (((Date) data[2]).before(now)) {
            message += "\"Ngay bat dau\" khong duoc truoc ngay hien tai\n";
        }

        if ((Date) data[3] == null) {
            message += "\"Ngay ket thuc\" khong de trong!\n";
        } else if (((Date) data[3]).before((Date) data[2])) {
            message += "\"Ngay ket thuc\" phai lon hon hoac bang \"Ngay bat dau\"!\n";
        }

//        if(khuyenMai.getTrangThai()) {
//            message += "\"Ten khuyen mai\" khong de trong!\n";
//        }
        if (((String) data[4]).isBlank()) {
            message += "\"Chiet khau\" khong de trong!\n";
        } else {
            try {
                float diem = Float.parseFloat((String) data[4]);
                if (diem < 0) {
                    message += "\"Chiet khau\" phai lon hon hoac bang 0!";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace(System.err);
                message += "\"Chiet khau\" phai la so, khong chua chu hoac ky tu dac biet!";
            }
        }
        return message;
    }

    //// Repo ChiNhanh
    @Override
    public List<ChiNhanhView> getAllChiNhanhON() { // chi lay chi nhanh dang hoat dong de them cho nhan vien
        return toDataChiNhanhView(khuyenMaiRepo.getAllChiNhanhON());
    }

    @Override
    public List<ChiNhanhView> getAllChiNhanh() { // dung cho admin 
        return toDataChiNhanhView(khuyenMaiRepo.getAllChiNhanh());
    }

    private List<ChiNhanhView> toDataChiNhanhView(List<ChiNhanh> chiNhanhs) {
        List<ChiNhanhView> chiNhanhViews = new ArrayList<>();
        if (!chiNhanhs.isEmpty()) {
            for (ChiNhanh cn : chiNhanhs) {
                ChiNhanhView cnv = toChiNhanhView(cn);
                chiNhanhViews.add(cnv);
            }
        }

        return chiNhanhViews;
    }

    private ChiNhanhView toChiNhanhView(ChiNhanh cn) {
        return new ChiNhanhView(cn.getId(), cn.getMa());
    }

    //// Repo SanPham
    private List<SanPhamViewModel> toDataSPView(List<SanPham> sanPhams) {
        List<SanPhamViewModel> sanPhamViews = new ArrayList<>();
        for (SanPham sp : sanPhams) {
            SanPhamViewModel spv = toSPView(sp);
            sanPhamViews.add(spv);
        }
        return sanPhamViews;
    }

    private SanPhamViewModel toSPView(SanPham sp) {
        return new SanPhamViewModel(sp.getId(), sp.getMa(), sp.getTen(), BigDecimal.valueOf(sp.getGiaBan()), sp.getAvatar());
    }
    
    @Override
    public List<SanPham> getAllSPByChiNhanh(ChiNhanh cn) {
        return khuyenMaiRepo.getAllSPByChiNhanh(cn);
    }

    @Override
    public List<SanPham> getAllSPByKhuyenMai(KhuyenMai km) {
        return khuyenMaiRepo.getAllSPByKhuyenMai(km);
    }

    @Override
    public List<SanPham> getAllSPByKhuyenMaiAndChiNhanh(ChiNhanh cn, KhuyenMai km) {
        return khuyenMaiRepo.getAllSPByKhuyenMaiAndChiNhanh(cn, km);
    }

    // ChiNhanh
//    @Override
//    public ChiNhanh getChiNhanhByNV() {
//        
//    }
    @Override
    public List<SanPham> getAllSP() {
        return khuyenMaiRepo.getAllSP();
    }
    @Override
    public List<SanPham> getAllSPByMa(String maSP) {
        return khuyenMaiRepo.getAllSPByMa(maSP);
    }
    
    @Override
    public List<SanPham> getAllSPByName(String tenSP) {
        return khuyenMaiRepo.getAllSPByName(tenSP);
    }
    
    @Override
    public List<SanPham> getAllSPByChiNhanhAndMa(ChiNhanh cn, String maSP) {
        return khuyenMaiRepo.getAllSPByChiNhanhAndMa(cn, maSP);
    }
    
    @Override
    public List<SanPham> getAllSPByChiNhanhAndName(ChiNhanh cn, String tenSP) {
        return khuyenMaiRepo.getAllSPByChiNhanhAndName(cn, tenSP);
    }
    
    @Override
    public SanPham getSanPhamByMa(String ma) {
        return khuyenMaiRepo.getSanPhamByMa(ma);
    }

    @Override
    public ChiNhanh getChiNhanhById(String id) {
        return khuyenMaiRepo.getChiNhanhById(id);
    }
}
