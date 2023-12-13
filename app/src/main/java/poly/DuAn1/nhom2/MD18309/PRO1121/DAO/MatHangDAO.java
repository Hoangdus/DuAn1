package poly.DuAn1.nhom2.MD18309.PRO1121.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.Database.DBFucker;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.MatHang;

public class MatHangDAO {

    private final DBFucker dbFucker;

    public MatHangDAO(Context context) {
        this.dbFucker = new DBFucker(context);
    }

    public ArrayList<MatHang> getMatHangList() {
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        ArrayList<MatHang> listMH = new ArrayList<>();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM MATHANG", null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    listMH.add(new MatHang(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getFloat(4), cursor.getString(5), cursor.getInt(7), cursor.getInt(6), cursor.getInt(8)));
                    cursor.moveToNext();
                }
                cursor.close();
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            database.endTransaction();
        }
        return listMH;
    }

    public boolean DeleteMatHang(int ID) {
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        boolean result = false;
        database.beginTransaction();
        ContentValues pairs = new ContentValues();
        pairs.put("STATUS", 1);
        try {
            long kq = database.update("MATHANG", pairs,"idMH=?", new String[]{String.valueOf(ID)});
            if (kq > -1) {
                result = true;
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public boolean AddMatHang(MatHang aMatHang) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        database.beginTransaction();
        ContentValues pair = new ContentValues();
//        pair.put("MaPM", aMatHang.getMaPM());
        pair.put("idNCC", aMatHang.getIdNhaCungCap());
        pair.put("idNH", aMatHang.getIdNganhHang());
        pair.put("TenMH", aMatHang.getTenMatHang());
        pair.put("SoLuong", aMatHang.getSoLuongMatHang());
        pair.put("DVT", aMatHang.getDonViTinh());
        pair.put("GiaNhap", aMatHang.getGiaNhapMatHang());
        pair.put("GiaBan", aMatHang.getGiaMatHang());
        pair.put("Status", aMatHang.getTrangThai());
        try {
            long kq = database.insert("MATHANG", null, pair);
            if (kq != -1) {
                result = true;
            }
            pair.clear();
            database.setTransactionSuccessful();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            database.endTransaction();
        }
        return result;
    }


    public MatHang getMatHangByID(int id) {
        MatHang matHang = null;
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM MATHANG Where idMH=?", new String[]{String.valueOf(id)});
            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                matHang = new MatHang(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getFloat(4), cursor.getString(5), cursor.getInt(7), cursor.getInt(6), cursor.getInt(8));
                cursor.close();
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            database.endTransaction();
        }
        return matHang;
    }

    public boolean updateMatHang(MatHang aMathang) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        ContentValues pairs = new ContentValues();
        pairs.put("idNCC", aMathang.getIdNhaCungCap());
        pairs.put("idNH", aMathang.getIdNganhHang());
        pairs.put("TenMH", aMathang.getTenMatHang());
        pairs.put("SoLuong", aMathang.getSoLuongMatHang());
        pairs.put("DVT", aMathang.getDonViTinh());
        pairs.put("GiaNhap", aMathang.getGiaNhapMatHang());
        pairs.put("GiaBan", aMathang.getGiaMatHang());
        pairs.put("STATUS", 0);
        database.beginTransaction();
        try {
            long kq = database.update("MatHang", pairs, "idMH=?", new String[]{String.valueOf(aMathang.getIdMatHang())});
            if (kq != -1) {
                result = true;
            }
            pairs.clear();
            database.setTransactionSuccessful();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            database.endTransaction();
        }
        return result;
    }
}