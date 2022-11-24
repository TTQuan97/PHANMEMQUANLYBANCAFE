/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import domainmodel.ChiTietPhieuNhap;
import domainmodel.HoaDonBanHang;
import domainmodel.NguyenLieu;
import domainmodel.NhaCungCap;
import domainmodel.NhanVien;
import domainmodel.PhieuNhapHang;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utility.Hibernateutility;

/**
 *
 * @author ASUS
 */
public class PhieuNhapRepo {

    public List<ChiTietPhieuNhap> getAllPhieuNhap() {
        List<ChiTietPhieuNhap> lstCtPhieuNhap = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            lstCtPhieuNhap = session.createQuery("from ChiTietPhieuNhap").list();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstCtPhieuNhap;
    }

    public List<NguyenLieu> getAllNguyenLieu() {
        List<NguyenLieu> lstNguyenLieu = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            lstNguyenLieu = session.createQuery("from NguyenLieu").list();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstNguyenLieu;
    }

    public List<NhaCungCap> getAllNhaCungCap() {
        List<NhaCungCap> lstNhaCungCap = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            lstNhaCungCap = session.createQuery("from NhaCungCap").list();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstNhaCungCap;
    }

    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> lstNhanVien = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            lstNhanVien = session.createQuery("from NhanVien").list();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstNhanVien;
    }


    public PhieuNhapHang getPhieuNhapByMa(String maPN) {
        PhieuNhapHang phieuNhapHang = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("From PhieuNhapHang Where Ma=:Ma");
            query.setParameter("Ma", maPN);
            phieuNhapHang = (PhieuNhapHang) query.uniqueResult();
            trans.commit();
            session.close();
        }
        return phieuNhapHang;
    }
    public NhanVien getNhanVienByMa(String maNhanVien) {
        NhanVien nhanVien = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("From NhanVien Where Ma=:Ma");
            query.setParameter("Ma", maNhanVien);
            nhanVien = (NhanVien) query.uniqueResult();
            trans.commit();
            session.close();
        }
        return nhanVien;
    }
    public NhaCungCap getNhaCungCapByMa(String maNhaCungCap) {
        NhaCungCap nhaCungCap = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("From NhaCungCap Where Ma=:Ma");
            query.setParameter("Ma", maNhaCungCap);
            nhaCungCap = (NhaCungCap) query.uniqueResult();
            trans.commit();
            session.close();
        }
        return nhaCungCap;
    }
    public NguyenLieu getNguyenLieuByMa(String maNguyenLieu) {
        NguyenLieu nguyenLieu = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("From NguyenLieu Where Ma=:Ma");
            query.setParameter("Ma", maNguyenLieu);
            nguyenLieu = (NguyenLieu) query.uniqueResult();
            trans.commit();
            session.close();
        }
        return nguyenLieu;
    }
    public PhieuNhapHang getPhieuNhapById(String idPhieuNhap){
        PhieuNhapHang pnh=null;
        try(Session session = Hibernateutility.getFactory().openSession()){
            Transaction trans = session.beginTransaction();
            pnh = session.get(PhieuNhapHang.class, idPhieuNhap);
            trans.commit();
            session.close();
        }
        return pnh;
    }
    public NguyenLieu getNguyenLieuById(String idNguyenLieu){
        NguyenLieu nl=null;
        try(Session session = Hibernateutility.getFactory().openSession()){
            Transaction trans = session.beginTransaction();
            nl = session.get(NguyenLieu.class, idNguyenLieu);
            trans.commit();
            session.close();
        }
        return nl;
    }

    public String updateTrangThaiPhieuNhap(String maPN, Integer trangThai) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            PhieuNhapHang phieuNhapHang = getPhieuNhapByMa(maPN);
            phieuNhapHang.setTrangThai(trangThai);
            session.update(phieuNhapHang);
            trans.commit();
            session.close();
            return "Thanh Cong";
        } catch (Exception e) {
            return "That bai";
        }
    }

    public String insertPhieuNhap(String maPN, NhaCungCap ncc, NhanVien nv, Date ngayNhap,int trangThai) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            PhieuNhapHang pnh = new PhieuNhapHang();
            pnh.setMa(maPN);
            pnh.setNhaCungCap(ncc);
            pnh.setNhanVien(nv);
            pnh.setNgayNhap(ngayNhap);
            pnh.setTrangThai(1);
        }
        return id;
    }

    public void insertCTPhieuNhap(PhieuNhapHang pn, NguyenLieu nl, float soLuongNhap, float donGia) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
            ctpn.setPhieuNhapKey(pn);
            ctpn.setNguyenLieuKey(nl);
            ctpn.setSoLuongNhap(soLuongNhap);
            ctpn.setDonGia(donGia);
            session.save(ctpn);
            trans.commit();
            session.close();
        }
    }
    public List<ChiTietPhieuNhap> searchPhieuNhap(String maPN) {
        Transaction trans = null;
        List<ChiTietPhieuNhap> listChiTiet = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            trans = session.beginTransaction();
            Query query = session.createQuery("FROM PhieuNhapHang WHERE Ma like :Ma");
            query.setParameter("Ma", "%" + maPN + "%");
            List<PhieuNhapHang> listSearch = query.list();
            for (PhieuNhapHang x : listSearch) {
                for (ChiTietPhieuNhap ctpn : x.getChiTietPhieuNhap()) {
                    listChiTiet.add(ctpn);
                }
            }
            trans.commit();
            session.close();
        } catch (Exception ex) {
            System.out.println("Lỗi ko thể tìm kiếm");
        }
        return listChiTiet;
    }
}
