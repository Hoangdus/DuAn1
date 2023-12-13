package poly.DuAn1.nhom2.MD18309.PRO1121.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.Database.DBFucker;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.CongViec;

public class CongViecDAO {
    private final DBFucker dbFucker;

    public CongViecDAO(Context context) {
        this.dbFucker = new DBFucker(context);
    }

    public ArrayList<CongViec> getCongViecList() {
        SQLiteDatabase database = dbFucker.getReadableDatabase();
        ArrayList<CongViec> listCV = new ArrayList<>();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM CONGVIEC", null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    listCV.add(new CongViec(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
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

        return listCV;
    }

    public boolean AddCongViec(CongViec congViec) {
        boolean result = false;
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("idCV", congViec.getIdCV());
        values.put("idQuanLy", congViec.getIdQuanLy());
        values.put("idNhanVien", congViec.getIdNhanVien());
        values.put("TieuDe", congViec.getTieuDe());
        values.put("MoTa", congViec.getMoTa());
        values.put("ThoiHan", congViec.getThoiHan());
        values.put("TrangThai", 0);
        try {
            long kq = database.insert("CONGVIEC", null, values);
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

    public boolean DeleteCongViec(int id) {
        SQLiteDatabase database = dbFucker.getWritableDatabase();
        boolean result = false;
        database.beginTransaction();
        try {
            long kq = database.delete("CONGVIEC", "idCV = ?", new String[]{String.valueOf(id)});
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

    public boolean updateCongViec(CongViec congViec) {
        boolean result = false;
        SQLiteDatabase database =dbFucker.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idQuanLy", congViec.getIdQuanLy());
        values.put("idNhanVien", congViec.getIdNhanVien());
        values.put("TieuDe", congViec.getTieuDe());
        values.put("MoTa", congViec.getMoTa());
        values.put("ThoiHan", congViec.getTrangThai());
        values.put("STATUS", 0);
        database.beginTransaction();
        try {
            long kq = database.update("CONGVIEC", values, "idCV = ?", new String[]{String.valueOf(congViec.getIdCV())});
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
