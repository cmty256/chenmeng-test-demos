package com.chenmeng.project.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chenmeng.project.constants.BasePageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 告警消息表
 *
 * @author chenmeng
 * @TableName alarm
 */
@TableName(value ="alarm")
@Data
public class AlarmDTO extends BasePageDTO implements Serializable {
    /**
     * 告警记录ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 企业所属镇街
     */
    private String enterpriseTown;

    /**
     * 企业所属市场监督所
     */
    private String marketSupervision;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 告警类型
     */
    private String alarmType;

    /**
     * 告警级别
     */
    private String alarmLevel;

    /**
     * 告警时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date alarmTime;

    /**
     * 告警图片路径
     */
    private String alarmImage;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AlarmDTO other = (AlarmDTO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEnterpriseTown() == null ? other.getEnterpriseTown() == null : this.getEnterpriseTown().equals(other.getEnterpriseTown()))
            && (this.getMarketSupervision() == null ? other.getMarketSupervision() == null : this.getMarketSupervision().equals(other.getMarketSupervision()))
            && (this.getDeviceName() == null ? other.getDeviceName() == null : this.getDeviceName().equals(other.getDeviceName()))
            && (this.getAlarmType() == null ? other.getAlarmType() == null : this.getAlarmType().equals(other.getAlarmType()))
            && (this.getAlarmLevel() == null ? other.getAlarmLevel() == null : this.getAlarmLevel().equals(other.getAlarmLevel()))
            && (this.getAlarmTime() == null ? other.getAlarmTime() == null : this.getAlarmTime().equals(other.getAlarmTime()))
            && (this.getAlarmImage() == null ? other.getAlarmImage() == null : this.getAlarmImage().equals(other.getAlarmImage()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEnterpriseTown() == null) ? 0 : getEnterpriseTown().hashCode());
        result = prime * result + ((getMarketSupervision() == null) ? 0 : getMarketSupervision().hashCode());
        result = prime * result + ((getDeviceName() == null) ? 0 : getDeviceName().hashCode());
        result = prime * result + ((getAlarmType() == null) ? 0 : getAlarmType().hashCode());
        result = prime * result + ((getAlarmLevel() == null) ? 0 : getAlarmLevel().hashCode());
        result = prime * result + ((getAlarmTime() == null) ? 0 : getAlarmTime().hashCode());
        result = prime * result + ((getAlarmImage() == null) ? 0 : getAlarmImage().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", enterpriseTown=").append(enterpriseTown);
        sb.append(", marketSupervision=").append(marketSupervision);
        sb.append(", deviceName=").append(deviceName);
        sb.append(", alarmType=").append(alarmType);
        sb.append(", alarmLevel=").append(alarmLevel);
        sb.append(", alarmTime=").append(alarmTime);
        sb.append(", alarmImage=").append(alarmImage);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String getAlarmTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(alarmTime);
    }

}