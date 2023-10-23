package com.example.project_study.data.gall

import jakarta.persistence.*

@Entity
@Table(name="GALLERY_IMAGE")
data class GalleryImage(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gallery_id", nullable = false)
    var gallery: Gallery,
    @Column(nullable = false)
    var path:String,
    @Column(nullable = false)
    var size: Long
)