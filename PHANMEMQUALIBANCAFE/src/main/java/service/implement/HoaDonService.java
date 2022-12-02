/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.implement;

import viewmodel.HoaDonViewModel;
import domainmodel.HoaDonBanHang;
import domainmodel.NhanVien;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import repository.HoaDonRepo;
import repository.PhieuNhapRepo;
import service.IHoaDon;

/**
 *
 * @author trant
 */
public class HoaDonService implements IHoaDon {

    private HoaDonRepo hoaDonRepo;

    public HoaDonService() {
        hoaDonRepo = new HoaDonRepo();
    }

    @Override
    public List<HoaDonViewModel> getAllHoaDon() {
        var hoaDon = hoaDonRepo.getAllHoaDon();
        List<HoaDonViewModel> lstView = new ArrayList<>();
        for (HoaDonBanHang x : hoaDon) {
            if (hoaDon != null) {
                 HoaDonViewModel qlhd = new HoaDonViewModel();
            qlhd.setId(x.getId());
            if (x.getMa() != null) {
                qlhd.setMaHoaDon(x.getMa());
            }
            if (x.getNgayTao() != null) {
                qlhd.setNgayTao(x.getNgayTao());
            }
            if (x.getNhanVien().getMa() != null) {
                qlhd.setMaNhanVien(x.getNhanVien().getMa());
            }
            if (x.getNhanVien().getHoTen() != null) {
                qlhd.setTenNhanVien(x.getNhanVien().getHoTen());
            }
            if (x.getTrangThai() != null) {
                qlhd.setTrangThai(x.getTrangThai());
            }
            lstView.add(qlhd);
            }
        }
        return lstView;
    }

    @Override
    public HoaDonBanHang getHoaDonByMa(String maHD) {
        return hoaDonRepo.getHoaDonByMa(maHD);
    }

    @Override
    public String updateTrangThai(String maHD, Integer trangThai) {
        return hoaDonRepo.updateTrangThai(maHD, trangThai);
    }

    @Override
    public List<HoaDonViewModel> locHoaDon(Date startDate, Date endDate) {
        var hoaDon = hoaDonRepo.locHoaDon(startDate, endDate);
        List<HoaDonViewModel> lstView = new ArrayList<>();
        for (HoaDonBanHang x : hoaDon) {
           HoaDonViewModel qlhd = new HoaDonViewModel();
            qlhd.setId(x.getId());
            if (x.getMa() != null) {
                qlhd.setMaHoaDon(x.getMa());
            }
            if (x.getNgayTao() != null) {
                qlhd.setNgayTao(x.getNgayTao());
            }
            if (x.getNhanVien().getMa() != null) {
                qlhd.setMaNhanVien(x.getNhanVien().getMa());
            }
            if (x.getNhanVien().getHoTen() != null) {
                qlhd.setTenNhanVien(x.getNhanVien().getHoTen());
            }
            if (x.getTrangThai() != null) {
                qlhd.setTrangThai(x.getTrangThai());
            }
            lstView.add(qlhd);
        }
        return lstView;
    }

    public static void main(String[] args) {
        HoaDonService hds = new HoaDonService();
        List<HoaDonViewModel> list = hds.getAllHoaDon();
        for (HoaDonViewModel hoaDonViewModel : list) {
            System.out.println(hoaDonViewModel.getTrangThai());
        }
    }
}
