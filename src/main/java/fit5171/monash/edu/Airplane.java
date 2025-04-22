package fit5171.monash.edu;

import java.util.HashMap;
import java.util.Map;

public class Airplane {
    private String airplaneID; // 飞机ID
    private String airplaneModel; // 飞机型号
    private int businessSeatsNumber; // 商务舱座位数
    private int economySeatsNumber; // 经济舱座位数
    private int crewSeatsNumber; // 机组座位数
    private Map<Character, Integer> seatRows; // 存储每排座位数

    /**
     * 构造函数：创建一个新的飞机对象
     * @param airplaneID 飞机ID
     * @param airplaneModel 飞机型号
     * @param businessSeatsNumber 商务舱座位数
     * @param economySeatsNumber 经济舱座位数
     * @param crewSeatsNumber 机组人员座位数
     * @throws IllegalArgumentException 当任何参数无效时抛出
     */
    public Airplane(String airplaneID, String airplaneModel, int businessSeatsNumber, int economySeatsNumber, int crewSeatsNumber) {
        // 验证飞机ID
        if (airplaneID == null || airplaneID.trim().isEmpty()) {
            throw new IllegalArgumentException("Airplane ID cannot be empty");
        }

        // 验证飞机型号
        if (airplaneModel == null || airplaneModel.trim().isEmpty()) {
            throw new IllegalArgumentException("Airplane model cannot be empty");
        }

        // 验证座位数量
        if (businessSeatsNumber < 0) {
            throw new IllegalArgumentException("Business seats cannot be negative");
        }

        if (economySeatsNumber < 0) {
            throw new IllegalArgumentException("Economy seats cannot be negative");
        }

        if (crewSeatsNumber < 0) {
            throw new IllegalArgumentException("Crew seats cannot be negative");
        }

        this.airplaneID = airplaneID;
        this.airplaneModel = airplaneModel;
        this.businessSeatsNumber = businessSeatsNumber;
        this.economySeatsNumber = economySeatsNumber;
        this.crewSeatsNumber = crewSeatsNumber;

        // 初始化座位排配置：A-J，每排7个座位
        initializeSeatRows();
    }

    /**
     * 初始化座位排标记 (A-J)，每排7个座位
     */
    private void initializeSeatRows() {
        seatRows = new HashMap<>();
        for (char row = 'A'; row <= 'J'; row++) {
            seatRows.put(row, 7); // 每排恰好有7个座位
        }
    }

    /**
     * 获取飞机ID
     * @return 飞机ID
     */
    public String getAirplaneID() {
        return airplaneID;
    }

    /**
     * 设置飞机ID
     * @param airplaneID 新的飞机ID
     * @throws IllegalArgumentException 当ID为空时抛出
     */
    public void setAirplaneID(String airplaneID) {
        if (airplaneID == null || airplaneID.trim().isEmpty()) {
            throw new IllegalArgumentException("Airplane ID cannot be empty");
        }
        this.airplaneID = airplaneID;
    }

    /**
     * 获取飞机型号
     * @return 飞机型号
     */
    public String getAirplaneModel() {
        return airplaneModel;
    }

    /**
     * 设置飞机型号
     * @param airplaneModel 新的飞机型号
     * @throws IllegalArgumentException 当型号为空时抛出
     */
    public void setAirplaneModel(String airplaneModel) {
        if (airplaneModel == null || airplaneModel.trim().isEmpty()) {
            throw new IllegalArgumentException("Airplane model cannot be empty");
        }
        this.airplaneModel = airplaneModel;
    }

    /**
     * 获取商务舱座位数
     * @return 商务舱座位数
     */
    public int getBusinessSeatsNumber() {
        return businessSeatsNumber;
    }

    /**
     * 设置商务舱座位数
     * @param businessSeatsNumber 新的商务舱座位数
     * @throws IllegalArgumentException 当座位数为负数时抛出
     */
    public void setBusinessSeatsNumber(int businessSeatsNumber) {
        if (businessSeatsNumber < 0) {
            throw new IllegalArgumentException("Business seats cannot be negative");
        }
        this.businessSeatsNumber = businessSeatsNumber;
    }

    /**
     * 获取经济舱座位数
     * @return 经济舱座位数
     */
    public int getEconomySeatsNumber() {
        return economySeatsNumber;
    }

    /**
     * 设置经济舱座位数
     * @param economySeatsNumber 新的经济舱座位数
     * @throws IllegalArgumentException 当座位数为负数时抛出
     */
    public void setEconomySeatsNumber(int economySeatsNumber) {
        if (economySeatsNumber < 0) {
            throw new IllegalArgumentException("Economy seats cannot be negative");
        }
        this.economySeatsNumber = economySeatsNumber;
    }

    /**
     * 获取机组人员座位数
     * @return 机组人员座位数
     */
    public int getCrewSeatsNumber() {
        return crewSeatsNumber;
    }

    /**
     * 设置机组人员座位数
     * @param crewSeatsNumber 新的机组人员座位数
     * @throws IllegalArgumentException 当座位数为负数时抛出
     */
    public void setCrewSeatsNumber(int crewSeatsNumber) {
        if (crewSeatsNumber < 0) {
            throw new IllegalArgumentException("Crew seats cannot be negative");
        }
        this.crewSeatsNumber = crewSeatsNumber;
    }

    /**
     * 获取座位排标记映射
     * @return 座位排标记映射
     */
    public Map<Character, Integer> getSeatRows() {
        return new HashMap<>(seatRows); // 返回副本以防止外部修改
    }

    /**
     * 获取特定排的座位数
     * @param row 排标记(A-J)
     * @return 该排的座位数
     * @throws IllegalArgumentException 当排标记无效时抛出
     */
    public int getSeatsInRow(char row) {
        if (row < 'A' || row > 'J') {
            throw new IllegalArgumentException("Row must be between A and J");
        }
        return seatRows.getOrDefault(row, 0);
    }

    /**
     * 设置特定排的座位数
     * @param row 排标记(A-J)
     * @param seats 座位数，必须为7
     * @throws IllegalArgumentException 当排标记无效或座位数不是7时抛出
     */
    public void setSeatsInRow(char row, int seats) {
        if (row < 'A' || row > 'J') {
            throw new IllegalArgumentException("Row must be between A and J");
        }
        if (seats != 7) {
            throw new IllegalArgumentException("Each row must contain exactly 7 seats");
        }
        seatRows.put(row, seats);
    }

    /**
     * 获取总座位数
     * @return 总座位数
     */
    public int getTotalSeats() {
        return businessSeatsNumber + economySeatsNumber + crewSeatsNumber;
    }

    /**
     * 返回飞机信息的字符串表示
     * @return 飞机信息的字符串表示
     */
    @Override
    public String toString() {
        return "Airplane{" +
                "id='" + getAirplaneID() + '\'' +
                ", model='" + getAirplaneModel() + '\'' +
                ", business seats=" + getBusinessSeatsNumber() +
                ", economy seats=" + getEconomySeatsNumber() +
                ", crew seats=" + getCrewSeatsNumber() +
                '}';
    }

    /**
     * 根据飞机ID获取飞机信息
     * @param airplane_id 飞机ID
     * @return 飞机对象，如果找不到则返回null
     */
    public static Airplane getAirPlaneInfo(String airplane_id) {
        // 此方法应该实现查询飞机数据库或集合并返回匹配的飞机
        return null;
    }
}