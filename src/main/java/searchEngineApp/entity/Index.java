package searchEngineApp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "_index")
public class Index {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int pageId;
    private int lemmaId;
    @Column(name = "_rank")
    private float rank;
    private int siteId;
}
