package fit5171.monash.edu;

import java.util.*;

/**
 * Airplane class
 * - 每排 7 个座位，行号 A-J
 * - 支持头等舱、经济舱、机组座
 */
public class Airplane {
    private int airplaneID;               // 飞机编号，必须大于 0
    private String airplaneModel;         // 飞机型号，非空

    private int businessSeatsNumber;      // 头等舱座位数
    private int economySeatsNumber;       // 经济舱座位数
    private int crewSeatsNumber;          // 机组座位数

    private List<Seat> seats;             // 所有座位列表

    // 常量：行标签 A-J、每排座位数
    private static final char[] ROWS = "ABCDEFGHIJ".toCharArray();
    private static final int SEATS_PER_ROW = 7;
    private static final int MAX_SEATS = ROWS.length * SEATS_PER_ROW; // 总共70个座位

    /**
     * 构造器：初始化飞机和座位
     */
    public Airplane(int airplaneID, String airplaneModel,
                    int businessSeatsNumber, int economySeatsNumber, int crewSeatsNumber) {
        // 验证基础信息
        if (airplaneID <= 0) throw new IllegalArgumentException("飞机 ID 必须大于 0");
        if (airplaneModel == null || airplaneModel.trim().isEmpty())
            throw new IllegalArgumentException("飞机型号不能为空");
        if (businessSeatsNumber < 0 || economySeatsNumber < 0 || crewSeatsNumber < 0)
            throw new IllegalArgumentException("座位数不能为负数");

        // 验证座位总数不超过最大容量
        int totalSeats = businessSeatsNumber + economySeatsNumber + crewSeatsNumber;
        if (totalSeats > MAX_SEATS) {
            throw new IllegalArgumentException("座位总数不能超过最大容量(" + MAX_SEATS + "个座位)");
        }

        this.airplaneID = airplaneID;
        this.airplaneModel = airplaneModel.trim();
        this.businessSeatsNumber = businessSeatsNumber;
        this.economySeatsNumber = economySeatsNumber;
        this.crewSeatsNumber = crewSeatsNumber;

        // 分配座位
        seats = new ArrayList<>();
        assignSeats(SeatClass.BUSINESS, businessSeatsNumber);
        assignSeats(SeatClass.ECONOMY, economySeatsNumber);
        assignSeats(SeatClass.CREW, crewSeatsNumber);
    }

    /**
     * 按照行和号依次分配座位
     */
    private void assignSeats(SeatClass type, int count) {
        int assigned = 0;
        for (char row : ROWS) {
            for (int num = 1; num <= SEATS_PER_ROW; num++) {
                if (assigned >= count) return;

                // 检查该位置是否已被分配
                if (!isSeatTaken(row, num)) {
                    seats.add(new Seat(row, num, type));
                    assigned++;
                }
            }
        }
    }

    /**
     * 检查座位是否已被分配
     */
    private boolean isSeatTaken(char row, int number) {
        for (Seat seat : seats) {
            if (seat.row == row && seat.number == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据行和座位号查找座位
     * @return 找到的座位，如果不存在返回null
     */
    public Seat findSeat(char row, int number) {
        for (Seat seat : seats) {
            if (seat.row == row && seat.number == number) {
                return seat;
            }
        }
        return null;
    }

    // Getter方法
    public int getAirplaneID() {
        return airplaneID;
    }

    public String getAirplaneModel() {
        return airplaneModel;
    }

    public int getBusinessSeatsNumber() {
        return businessSeatsNumber;
    }

    public int getEconomySeatsNumber() {
        return economySeatsNumber;
    }

    public int getCrewSeatsNumber() {
        return crewSeatsNumber;
    }

    public int getTotalSeatsNumber() {
        return businessSeatsNumber + economySeatsNumber + crewSeatsNumber;
    }

    public List<Seat> getSeats() {
        return new ArrayList<>(seats); // 返回副本防止外部修改
    }

    /**
     * 获取特定类型的座位列表
     */
    public List<Seat> getSeatsByType(SeatClass type) {
        List<Seat> result = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.type == type) {
                result.add(seat);
            }
        }
        return result;
    }

    /**
     * 获取特定行的座位数
     */
    public int getSeatsInRow(char row) {
        if (row < 'A' || row > 'J') {
            throw new IllegalArgumentException("行标识必须在A到J之间");
        }

        int count = 0;
        for (Seat seat : seats) {
            if (seat.row == row) {
                count++;
            }
        }
        return count;
    }

    // 简单输出飞行信息
    @Override
    public String toString() {
        return "Airplane[ID=" + airplaneID + ", model=" + airplaneModel +
                ", business=" + businessSeatsNumber +
                ", economy=" + economySeatsNumber +
                ", crew=" + crewSeatsNumber + "]";
    }

    /**
     * 根据飞机ID获取飞机信息（静态方法）
     * @param airplaneID 飞机ID
     * @return 飞机对象，如果找不到则返回null
     */
    public static Airplane getAirplaneInfo(int airplaneID) {
        // 此方法应该实现查询飞机数据库或集合并返回匹配的飞机
        return null;
    }

    /**
     * 座位类别
     */
    public enum SeatClass { BUSINESS, ECONOMY, CREW }

    /**
     * 表示一张座位：行 (A-J), 号 (1-7), 和类别
     */
    public static class Seat {
        public final char row;      // 行标签
        public final int number;    // 座位号
        public final SeatClass type; // 座位类别

        public Seat(char row, int number, SeatClass type) {
            // 验证座位行和号的有效性
            if (row < 'A' || row > 'J') {
                throw new IllegalArgumentException("行标识必须在A到J之间");
            }
            if (number < 1 || number > SEATS_PER_ROW) {
                throw new IllegalArgumentException("座位号必须在1到" + SEATS_PER_ROW + "之间");
            }

            this.row = row;
            this.number = number;
            this.type = type;
        }

        @Override
        public String toString() {
            return row + "" + number + "(" + type + ")";
        }
    }
}