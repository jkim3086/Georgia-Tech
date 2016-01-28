from tkinter import *
import datetime
import pymysql

class popularReport:
    def __init__(r):
        
        r.root = Tk()
        r.root.configure(bg="gold")    #### I tried to seperate windows with 2 sperate Frames, but if label is in a Frame, Frame's bg color doesn't function. I searched but they say OSX doesn't support it. do you have any solution?

        label = Label(r.root, text="popular book report", bg = "gold")
        label.grid(row = 0, columnspan = 4, padx = 5,pady=5,sticky=W)

        label2 = Label(r.root, text="Month-----",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        label3 = Label(r.root, text="UserName-----",bg="gold").grid(row = 1, column = 1,padx=5, pady = 5,sticky = E)
        label4 = Label(r.root, text="# of checkouts-",bg="gold").grid(row = 1, column = 2,padx=5, pady = 5,sticky = E)

        r.showReport()
        r.root.mainloop()
        
    def connect(r):
        try:
            r.db = pymysql.connect(host = 'academic-mysql.cc.gatech.edu', passwd = 'LeQs1rXt' , user = 'cs4400_Group_75', db='cs4400_Group_75')
            r.cursor = r.db.cursor()
        except:
            return None

        
    def showReport(r):
        r.connect()
        r.labellist = []
        sql = '''(SELECT MONTH(ISSUED_BOOK.DATE_OF_ISSUED) AS 'Month', ISSUED_BOOK.USERNAME AS 'User Name', COUNT(*) AS '#checkouts'
        FROM ISSUED_BOOK
        WHERE ISSUED_BOOK.RETURN_DATE <= %s AND ISSUED_BOOK.RETURN_DATE > %s AND (ISSUED_BOOK.COUNT_EXTENSION = 0 OR ISSUED_BOOK.COUNT_EXTENSION IS NULL)
        GROUP BY USERNAME
        HAVING COUNT(*) > 1
        ORDER BY COUNT(*) DESC
        LIMIT 0, 5)
        UNION
        (SELECT MONTH(ISSUED_BOOK.DATE_OF_ISSUED) AS 'Month', ISSUED_BOOK.USERNAME AS 'User Name', COUNT(*) AS '#checkouts'
        FROM ISSUED_BOOK
        WHERE ISSUED_BOOK.RETURN_DATE <= %s AND ISSUED_BOOK.RETURN_DATE > %s AND (ISSUED_BOOK.COUNT_EXTENSION = 0 OR ISSUED_BOOK.COUNT_EXTENSION IS NULL)
        GROUP BY USERNAME
        HAVING COUNT(*) > 1
        ORDER BY COUNT(*) DESC
        LIMIT 0, 5)'''

        cy = datetime.date(2010, 1, 1).strftime("%Y")
        ##cy = datetime.date.today().strftime("%Y")
        feb = datetime.date(int(cy), 2, 14)
        mar = datetime.date(int(cy), 3, 14)
        jan  = datetime.date(int(cy), 1, 14)
        r.cursor.execute(sql, (feb, jan, mar, feb))
        tuplelist = r.cursor.fetchall()
        
        labellist = []
        i = 2
        dic = {1:'Jan',2:'Feb'}
        
        for e in tuplelist:
            labellist.append(Label(r.root, text = dic[int(e[0])]).grid(row=i, column = 0))
            labellist.append(Label(r.root, text = e[1]).grid(row=i, column = 1))
            labellist.append(Label(r.root, text = e[2]).grid(row=i, column = 2))
            i += 1

        
            
popularReport()
