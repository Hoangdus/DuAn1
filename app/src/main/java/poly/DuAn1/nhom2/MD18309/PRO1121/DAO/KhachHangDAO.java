package poly.DuAn1.nhom2.MD18309.PRO1121.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.DBFucker;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.KhachHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.NganhHang;

public class KhachHangDAO {
    private final DBFucker dbFucker;

    public KhachHangDAO(Context context) {
        this.dbFucker = new DBFucker(context);
    }

    public ArrayList<KhachHang> getKhachHangList() {
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        ArrayList<KhachHang> listKH = new ArrayList<>();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM KHACHHANG", null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    listKH.add(new KhachHang(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(3),
                            cursor.getString(2),
                            cursor.getInt(4)));
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

        return listKH;
    }

    public boolean AddKhachHang(KhachHang khachhang) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("HoTen", khachhang.getHoTenKH());
        values.put("Email", khachhang.getAddressKH());
        values.put("SdtKH", khachhang.getPhoneKH());
        values.put("STATUS", 0);
        try {
            long kq = database.insert("KHACHHANG", null, values);
            if (kq != -1) {
                result = true;
            }
            values.clear();
            database.setTransactionSuccessful();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public boolean DeleteKhachHang(int id) {
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        boolean result = false;
        database.beginTransaction();
        ContentValues pairs = new ContentValues();
        pairs.put("STATUS", 1);
        try {
            long kq = database.update("KHACHHANG", pairs, "idKH = ?", new String[]{String.valueOf(id)});
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

    public boolean updateKhachHang(KhachHang khachHang) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HoTen", khachHang.getHoTenKH());
        values.put("Email", khachHang.getAddressKH());
        values.put("SdtKH", khachHang.getPhoneKH());
        values.put("STATUS", 0);
        database.beginTransaction();
        try {
            long kq = database.update("KHACHHANG", values, "idKH = ?", new String[]{String.valueOf(khachHang.getIdKH())});
            if (kq != -1) {
                result = true;
            }
            values.clear();
            database.setTransactionSuccessful();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            database.endTransaction();
        }

        return result;
    }

    public KhachHang getKhachHangbyID(int id){
        KhachHang khachHang = null;
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        database.beginTransaction();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM KHACHHANG Where idKH=?", new String[]{String.valueOf(id)});
            if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()){
                khachHang = new KhachHang(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(2),
                        cursor.getInt(4));
                cursor.close();
            }
            database.setTransactionSuccessful();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            database.endTransaction();
        }
        return khachHang;
    }
}
