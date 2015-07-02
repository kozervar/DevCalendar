package devcalendar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Marcin Kozaczyk on 2015-07-02.
 */
@MappedSuperclass
@Data
@EqualsAndHashCode( callSuper = false )
public class Identifiable  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column( name = "id", insertable=false, updatable=false)
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    public Identifiable()
    {
        super();
    }
}
