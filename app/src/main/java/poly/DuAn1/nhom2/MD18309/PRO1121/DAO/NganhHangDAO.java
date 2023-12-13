package poly.DuAn1.nhom2.MD18309.PRO1121.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.DBFucker;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.NganhHang;

public class NganhHangDAO {
    private final DBFucker dbFucker;

    public NganhHangDAO(Context context) {
        this.dbFucker = new DBFucker(context);
    }

    public ArrayList<NganhHang> getNganhHangList(){
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        ArrayList<NganhHang> listNH = new ArrayList<>();
        database.beginTransaction();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM NGANHHANG", null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    listNH.add(new NganhHang(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
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
        return listNH;
    }

    public boolean AddNganhHang(NganhHang nganhHang) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
//        values.put("idNH", nganhHang.getIdNganhHang());
        values.put("TenNH", nganhHang.getTenNganhHang());
        values.put("STATUS", 0);
        try {
            long kq = database.insert("NGANHHANG", null, values);
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

    public boolean DeleteNganhHang(int id) {
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);
        database.beginTransaction();
        try {
            long kq = database.update("NGANHHANG", values,"idNH = ?", new String[]{String.valueOf(id)});
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

    public boolean updateNganhHang(NganhHang nganhHang) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenNH", nganhHang.getTenNganhHang());
        values.put("STATUS", 0);
        database.beginTransaction();
        try {
            long kq = database.update("NGANHHANG", values, "idNH = ?", new String[]{String.valueOf(nganhHang.getIdNganhHang())});
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

    public NganhHang getNganhHangByID(int id){
        NganhHang nganhHang = null;
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        database.beginTransaction();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM NGANHHANG Where idNH=?", new String[]{String.valueOf(id)});
            if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()){
                nganhHang = new NganhHang(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                cursor.close();
            }
            database.setTransactionSuccessful();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            database.endTransaction();
        }
        return nganhHang;
    }
}
