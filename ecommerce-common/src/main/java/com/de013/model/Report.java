package com.de013.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.de013.dto.ReportVO;
import com.de013.utils.JConstants.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(length = 50)
    private String type;

    private BigDecimal amount = BigDecimal.ZERO;

    @Column(length = 50)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public Report(String type, Date date) {
        this.status = Status.ACTIVE.name();
        this.date = date;
		this.createDate = new Date();
		this.createDate = new Date();
		this.amount = new BigDecimal(0);
		this.type = type;
    }

    @JsonIgnore
    public ReportVO getVO() {
        ReportVO reportVO = new ReportVO();
        BeanUtils.copyProperties(this, reportVO);

        return reportVO;
    }
}
