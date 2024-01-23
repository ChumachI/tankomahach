package edu.tanks.server;

import com.zaxxer.hikari.HikariDataSource;
import edu.tanks.model.Result;
import edu.tanks.repository.ResultsRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Scanner;

public class Server {

    public void start(int port) {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while (true){
                Socket firstPlayerSocket = serverSocket.accept();
                Socket secondPlayerSocket = serverSocket.accept();
                ResultsRepository resultsRepository = new ResultsRepository(getDataSource());

                new Thread(()->{
                    boolean exitFlag = false;
                    try {
                        PrintWriter fpOut = new PrintWriter(firstPlayerSocket.getOutputStream());
                        Scanner spIn = new Scanner(secondPlayerSocket.getInputStream());
                        fpOut.println("start");
                        fpOut.flush();
                        while (!exitFlag){
                            String income = spIn.nextLine();
                            fpOut.println(income);
                            fpOut.flush();
                            if(income.equals("exit")){
                                exitFlag = true;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Первый игрок отключился");
                        try {
                            firstPlayerSocket.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }).start();
                new Thread(()->{
                    boolean exitFlag = false;
                    try {
                        PrintWriter spOut = new PrintWriter(secondPlayerSocket.getOutputStream());
                        Scanner fpIn = new Scanner(firstPlayerSocket.getInputStream());
                        spOut.println("start");
                        spOut.flush();
                        while (!exitFlag){
                            String income = fpIn.nextLine();
                            spOut.println(income);
                            spOut.flush();
                            if(income.equals("exit")){
                                exitFlag = true;
                            } else if(income.startsWith("result")){
                                String[] statistics = income.split(" ");
                                int p1Shots = Integer.parseInt(statistics[1]);
                                int p2Hits = Integer.parseInt(statistics[2]);

                                int p2Shots = Integer.parseInt(statistics[3]);
                                int p1Hits = Integer.parseInt(statistics[4]);
                                int p1Misses = p1Shots - p1Hits;
                                int p2Misses = p2Shots - p2Hits;
                                Result result = new Result(p1Shots, p1Hits, p1Misses, p2Shots, p2Hits, p2Misses, new Timestamp(System.currentTimeMillis()));
                                resultsRepository.save(result);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Второй игрок отключился");
                        try {
                            secondPlayerSocket.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }).start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/test");
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("123456");
        return dataSource;
    }
}
