package poly.DuAn1.nhom2.MD18309.PRO1121.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.DBFucker;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.HoaDon;

public class HoaDonDAO {
    private final DBFucker dbFucker;

    public HoaDonDAO(Context context) {
        this.dbFucker = new DBFucker(context);
    }

    public ArrayList<HoaDon> getHoaDonList() {
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        ArrayList<HoaDon> listHD = new ArrayList<>();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM HOADON", null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    listHD.add(new HoaDon(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getInt(5),
                            cursor.getInt(6)));
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

        return listHD;
    }

    public boolean AddHoaDon(HoaDon hoaDon) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("idHD", hoaDon.getIdHD());
        values.put("idNguoiTao", hoaDon.getIdNguoiTao());
        values.put("idNguoiMua", hoaDon.getIdNguoiMua());
        values.put("NgayTao", hoaDon.getNgayTao());
        values.put("TrangThai", 0);
        try {
            long kq = database.insert("HOADON", null, values);
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

    public boolean DeleteHoaDon(int id) {
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        boolean result = false;
        database.beginTransaction();
        try {
            long kq = database.delete("HOADON", "idHD = ?", new String[]{String.valueOf(id)});
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

    public boolean updateHoaDon(HoaDon hoaDon) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idNguoiTao", hoaDon.getIdNguoiTao());
        values.put("idNguoiMua", hoaDon.getIdNguoiMua());
        values.put("NgayTao", hoaDon.getNgayTao());
        values.put("TrangThai", 0);
        database.beginTransaction();
        try {
            long kq = database.update("HOADON", values, "idHD = ?", new String[]{String.valueOf(hoaDon.getIdHD())});
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
