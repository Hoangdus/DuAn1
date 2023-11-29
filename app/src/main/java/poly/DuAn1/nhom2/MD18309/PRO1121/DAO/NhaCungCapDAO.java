package poly.DuAn1.nhom2.MD18309.PRO1121.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.DBFucker;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.NhaCungCap;

public class NhaCungCapDAO {

    private DBFucker dbFucker;

    public NhaCungCapDAO(Context context) {
        this.dbFucker = new DBFucker(context);
    }

    public ArrayList<NhaCungCap> getNhaCungCapList(){
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        ArrayList<NhaCungCap> listNCC = new ArrayList<>();
        database.beginTransaction();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM NhaCungCap", null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    listNCC.add(new NhaCungCap(cursor.getInt(0), cursor.getString(1), cursor.getString(4), cursor.getString(2), cursor.getString(3), cursor.getInt(5)));
                    cursor.moveToNext();
                }
                cursor.close();
            }
            database.setTransactionSuccessful();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            database.endTransaction();
        }
        return listNCC;
    }

    public boolean AddNhaCungCap(NhaCungCap nhaCungCap) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("idNCC", nhaCungCap.getIdNhaCungCap());
        values.put("TenNCC", nhaCungCap.getTenNhaCungCap());
        values.put("NguoiDaiDien", nhaCungCap.getHoTenNguoiDaiDien());
        values.put("DiaChi", nhaCungCap.getDiaChiNhaCungCap());
        values.put("Sdt", nhaCungCap.getSdtNhaCungCap());
        values.put("STATUS", 0);
        try {
            long kq = database.insert("NHACUNGCAP", null, values);
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

    public boolean DeleteNhaCungCap(int id) {
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        boolean result = false;
        database.beginTransaction();
        try {
            long kq = database.delete("NHACUNGCAP", "idNCC = ?", new String[]{String.valueOf(id)});
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

    public boolean updateNhaCungCap(NhaCungCap nhaCungCap) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenNCC", nhaCungCap.getTenNhaCungCap());
        values.put("NguoiDaiDien", nhaCungCap.getHoTenNguoiDaiDien());
        values.put("DiaChi", nhaCungCap.getDiaChiNhaCungCap());
        values.put("Sdt", nhaCungCap.getSdtNhaCungCap());
        values.put("STATUS", 0);
        database.setTransactionSuccessful();
        try {
            long kq = database.update("NHACUNGCAP", values, "idNCC = ?", new String[]{String.valueOf(nhaCungCap.getIdNhaCungCap())});
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

    public NhaCungCap getNhaCungCapByID(int id){
        NhaCungCap nhaCungCap = null;
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        database.beginTransaction();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM NHACUNGCAP Where idNCC=?", new String[]{String.valueOf(id)});
            if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()){
                nhaCungCap = new NhaCungCap(cursor.getInt(0), cursor.getString(1), cursor.getString(4), cursor.getString(2), cursor.getString(3), cursor.getInt(5));
                cursor.close();
            }
            database.setTransactionSuccessful();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            database.endTransaction();
        }
        return nhaCungCap;
    }
}
