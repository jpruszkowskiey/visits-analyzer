# Finding the time period algorithm
1. Method **Program.run** reads visits data using **CsvVisitReader** class
2. Read collection of visits is passed to **VisitAnalyzer.getMostCrowdedPeriod** method
3. The method performs operations:
    - creates list of **VisitEvent** - two events for each **Visit**
    - each **VisitEvent** contains a type (**ENTRANCE** or **LEAVE**) and event time
    - then the list is sorted by event time
        - if the event time is the same for **ENTRANCE** and **LEAVE** events then **ENTRANCE** goes first
    - for each loop iterates over sorted events to find **start**, **end** and **maxCount** values
        - set **currentTime** as event time
        - increments or decrements **currentCount** depending on event type (**ENTRANCE** or **LEAVE**)
        - if **currentCount** is greater or equal **maxCount**
            - **currentCount** is assigned to **maxCount**
            - **start** value is taken fom **currentTime**
            - **end** value is taken fom **currentTime**
        - if **currentCount** was equal to **maxCount** before current iteration, but after iteration value is smaller
            - then take **currentTime** and assign it to **end**
    - return result containing **start**, **end** and **maxCount**
