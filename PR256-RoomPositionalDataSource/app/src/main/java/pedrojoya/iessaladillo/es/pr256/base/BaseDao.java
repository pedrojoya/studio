package pedrojoya.iessaladillo.es.pr256.base;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

// M for Model
@SuppressWarnings({"UnusedReturnValue", "unused"})
public abstract class BaseDao<M> {

    @Insert
    public abstract long insert(M model);

    @Update
    public abstract int update(M model);

    @Delete
    public abstract int delete(M model);

}
