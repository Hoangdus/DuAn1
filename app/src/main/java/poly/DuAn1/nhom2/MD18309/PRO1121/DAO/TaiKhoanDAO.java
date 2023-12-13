package poly.DuAn1.nhom2.MD18309.PRO1121.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.Database.DBFucker;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.TaiKhoan;

public class TaiKhoanDAO {
    private final DBFucker dbFucker;

    public TaiKhoanDAO(Context context) {
        this.dbFucker = new DBFucker(context);
    }

    public ArrayList<TaiKhoan> getListTaiKhoan(){
        ArrayList<TaiKhoan> taiKhoanArrayList = new ArrayList<>();
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        database.beginTransaction();
        try{
            Cursor cursor = database.rawQuery("SELECT UserName, ROLE, HOTEN, PHONE, EMAIL, STATUS FROM TAIKHOAN", null);
            if (cursor != null && cursor.getCount() >0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    taiKhoanArrayList.add(new TaiKhoan(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5)));
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
        return taiKhoanArrayList;
    }

    public TaiKhoan login(String tk, String mk){
        TaiKhoan taiKhoan = null;
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT ROLE, HOTEN, PHONE, EMAIL, STATUS FROM TAIKHOAN WHERE UserName=? AND Password=?", new String[]{tk, mk});
            if (cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                taiKhoan = new TaiKhoan(tk, cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
                cursor.close();
            }
            database.setTransactionSuccessful();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            database.endTransaction();
        }
        return taiKhoan;
    }

    public boolean AddTaiKhoan(TaiKhoan aTaiKhoan){
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        database.beginTransaction();
        ContentValues pair = new ContentValues();
        pair.put("UserName", aTaiKhoan.getUserName());
        pair.put("Password", aTaiKhoan.getPassWord());
        pair.put("ROLE", aTaiKhoan.getRole());
        pair.put("HoTen", aTaiKhoan.getHoTen());
        pair.put("Phone", aTaiKhoan.getPhone());
        pair.put("Email", aTaiKhoan.getEmail());
        pair.put("STATUS", 0);
        try {
            long kq = database.insert("TAIKHOAN", null, pair);
            if (kq != -1){
                result = true;
            }
            pair.clear();
            database.setTransactionSuccessful();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            database.endTransaction();
        }
        return result;
    }

    public boolean DeleteTaiKhoan(String username) {
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        boolean result = false;
        database.beginTransaction();
        ContentValues pairs = new ContentValues();
        pairs.put("STATUS", 1);
        try {
            long kq = database.update("TAIKHOAN", pairs, "UserName = ?", new String[]{username});
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

    public boolean updateTaiKhoan(TaiKhoan taiKhoan) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Password", taiKhoan.getPassWord());
        values.put("ROLE", taiKhoan.getRole());
        values.put("HoTen", taiKhoan.getHoTen());
        values.put("Phone", taiKhoan.getPhone());
        values.put("Email", taiKhoan.getEmail());
        values.put("STATUS", 0);
        database.beginTransaction();
        try {
            long kq = database.update("TAIKHOAN", values, "UserName = ?", new String[]{taiKhoan.getUserName()});
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

    public boolean checkUserName(String tk){
        boolean result = false;
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        database.beginTransaction();
        String checkQuerry;
        try{
            checkQuerry = "SELECT UserName FROM TAIKHOAN WHERE UserName = ?";
            Cursor cursor = database.rawQuery(checkQuerry, new String[]{tk});
            if (cursor != null && cursor.getCount() > 0){
                if (cursor.moveToFirst()){
                    result = true;
                }
                cursor.close();
                database.setTransactionSuccessful();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            database.endTransaction();
        }
        return result;
    }

    public TaiKhoan getTaiKhoan(String tk){
        TaiKhoan taiKhoan = null;
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        database.beginTransaction();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM TAIKHOAN WHERE UserName = ?", new String[]{tk});
            if (cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                taiKhoan = new TaiKhoan(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
                cursor.close();
            }
            database.setTransactionSuccessful();
            }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            database.endTransaction();
        }
        return taiKhoan;
    }

    public boolean changePass(String userName, String newPass) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Password", newPass);
        database.beginTransaction();
        String sqlQuerry = "";
        try {
            long kq = database.update("TAIKHOAN", values, "UserName = ?", new String[]{userName});
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




}
