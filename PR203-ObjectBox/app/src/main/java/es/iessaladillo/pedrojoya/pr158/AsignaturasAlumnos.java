package es.iessaladillo.pedrojoya.pr158;

import io.objectbox.BoxStore;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Generated;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Relation;
import io.objectbox.annotation.apihint.Internal;
import io.objectbox.exception.DbDetachedException;
import io.objectbox.relation.ToOne;
import io.objectbox.Box;
import io.objectbox.exception.DbException;

@SuppressWarnings({"WeakerAccess", "unused"})
@Entity
public class AsignaturasAlumnos {

    @Id
    long id;

    @Index
    long alumnoId;

    long asignaturaId;

    @Relation
    Alumno alumno;

    @Relation
    Asignatura asignatura;

    /**
     * Used to resolve relations
     */
    @Internal
    @Generated(hash = 1307364262)
    transient BoxStore __boxStore;

    @Generated(hash = 1593200419)
    public AsignaturasAlumnos(long id, long alumnoId, long asignaturaId) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.asignaturaId = asignaturaId;
    }

    @Generated(hash = 2007504098)
    public AsignaturasAlumnos() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public long getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(long asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    @Internal
    @Generated(hash = 2091083903)
    private transient ToOne<AsignaturasAlumnos, Alumno> alumno__toOne;

    /**
     * See {@link io.objectbox.relation.ToOne} for details.
     */
    @Generated(hash = 1622299629)
    public synchronized ToOne<AsignaturasAlumnos, Alumno> getAlumno__toOne() {
        if (alumno__toOne == null) {
            alumno__toOne = new ToOne<>(this, AsignaturasAlumnos_.alumnoId, Alumno.class);
        }
        return alumno__toOne;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 2078347351)
    public Alumno getAlumno() {
        alumno = getAlumno__toOne().getTarget(this.alumnoId);
        return alumno;
    }

    /**
     * Set the to-one relation including its ID property.
     */
    @Generated(hash = 1535328554)
    public void setAlumno(Alumno alumno) {
        getAlumno__toOne().setTarget(alumno);
        this.alumno = alumno;
    }

    @Internal
    @Generated(hash = 1821759822)
    private transient ToOne<AsignaturasAlumnos, Asignatura> asignatura__toOne;

    /**
     * See {@link io.objectbox.relation.ToOne} for details.
     */
    @Generated(hash = 1259345634)
    public synchronized ToOne<AsignaturasAlumnos, Asignatura> getAsignatura__toOne() {
        if (asignatura__toOne == null) {
            asignatura__toOne = new ToOne<>(this, AsignaturasAlumnos_.asignaturaId,
                    Asignatura.class);
        }
        return asignatura__toOne;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1436147894)
    public Asignatura getAsignatura() {
        asignatura = getAsignatura__toOne().getTarget(this.asignaturaId);
        return asignatura;
    }

    /**
     * Set the to-one relation including its ID property.
     */
    @Generated(hash = 1330900196)
    public void setAsignatura(Asignatura asignatura) {
        getAsignatura__toOne().setTarget(asignatura);
        this.asignatura = asignatura;
    }

    /**
     * Removes entity from its object box. Entity must attached to an entity context.
     */
    @Generated(hash = 592009007)
    public void remove() {
        if (__boxStore == null) {
            throw new DbDetachedException();
        }
        __boxStore.boxFor(AsignaturasAlumnos.class).remove(this);
    }

    /**
     * Puts the entity in its object box.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 741551164)
    public void put() {
        if (__boxStore == null) {
            throw new DbDetachedException();
        }
        __boxStore.boxFor(AsignaturasAlumnos.class).put(this);
    }

}
