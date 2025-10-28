package com.juaracoding.service;

import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.dto.RelationDTO;
import com.juaracoding.dto.validation.ValProdukDTO;
import com.juaracoding.handler.ResponseHandler;
import com.juaracoding.model.KategoriProduk;
import com.juaracoding.model.Produk;
import com.juaracoding.model.Supplier;
import com.juaracoding.repo.ProdukRepo;
import com.juaracoding.util.LoggingFile;
import com.juaracoding.util.RequestCapture;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * platform code : SLS
 * modul code : 03
 */
@Service
@Transactional
public class ProdukService implements IService<Produk>, IReport<Produk> {

    @Autowired
    private ProdukRepo produkRepo;

    private static final String className = "ProdukService";

    @Override//001-010
    public ResponseEntity<Object> save(Produk produk, HttpServletRequest request) {

        if(produk==null){
            return new ResponseHandler().handleResponse("Data Gagal Disimpan",
                    HttpStatus.BAD_REQUEST,null,"SLS03FV001",request);
        }
        try{
            produkRepo.save(produk);
        }catch (Exception e){
            LoggingFile.logException(className,"save(Produk produk, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Data Gagal Disimpan",
                    HttpStatus.BAD_REQUEST,null,"SLS03FE001",request);
        }
        return new ResponseHandler().handleResponse("Data Berhasil Disimpan",
                HttpStatus.CREATED,null,null,request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Produk produk, HttpServletRequest request) {
        if(produk==null){
            return new ResponseHandler().handleResponse("Data Gagal Diubah",
                    HttpStatus.BAD_REQUEST,null,"SLS03FV011",request);
        }
        try{
            Optional<Produk> optionalProduk = produkRepo.findById(id);
            if(optionalProduk.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS03FV012",request);
            }
            Produk nextProduk = optionalProduk.get();
            nextProduk.setKategoriProduk(produk.getKategoriProduk());
            nextProduk.setNama(produk.getNama());
            nextProduk.setSupplierList(produk.getSupplierList());
            nextProduk.setModifiedBy(1L);

        }catch (Exception e){
            LoggingFile.logException(className,"update(Long id,Produk produk, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Data Gagal Diubah",
                    HttpStatus.BAD_REQUEST,null,"SLS03FE011",request);
        }
        return new ResponseHandler().handleResponse("Data Berhasil Diubah",
                HttpStatus.OK,null,null,request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        if(id==null){
            return new ResponseHandler().handleResponse("Data Gagal Dihapus",
                    HttpStatus.BAD_REQUEST,null,"SLS03FV021",request);
        }
        try{
            Optional<Produk> optionalProduk = produkRepo.findById(id);
            if(optionalProduk.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS03FV022",request);
            }
            produkRepo.deleteById(id);
        }catch (Exception e){
            LoggingFile.logException(className,"delete(Long id, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Data Gagal Dihapus",
                    HttpStatus.BAD_REQUEST,null,"SLS03FE021",request);
        }
        return new ResponseHandler().handleResponse("Data Berhasil Dihapus",
                HttpStatus.OK,null,null,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> uploadDataExcel(MultipartFile file, HttpServletRequest request) {
        return null;
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request) {

    }

    @Override
    public void downloadReportPDF(String column, String value, HttpServletRequest request) {

    }

    @Override
    public List<Produk> convertListWorkBookToListEntity(List<Map<String, String>> map) {
        return List.of();
    }

    public Produk mapDtoToEntity(ValProdukDTO valProdukDTO){
        Produk produk = new Produk();
        produk.setNama(valProdukDTO.getNama());
        KategoriProduk k = new KategoriProduk();
        k.setId(valProdukDTO.getRelationDTO().getId());
        produk.setKategoriProduk(k);
        List<Supplier> l = new ArrayList<>();
        for (RelationDTO r:
             valProdukDTO.getRelationDTOList()) {
            Supplier s = new Supplier();
            s.setId(r.getId());
            l.add(s);
        }
        produk.setSupplierList(l);
        return produk;
    }
}
