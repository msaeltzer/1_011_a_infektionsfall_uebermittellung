export const columns_data = [
    {
        title: 'PLZ',
        dataIndex: 'zip',
        key: 'zip',
        width: 10,
        sorter: (a: any, b: any) => a.zip - b.zip,
        defaultSortOrder: "ascend",
    },
    {
        title: 'Aktive Fälle (Infizierte)',
        dataIndex: 'active',
        key: 'active',
        width: 10,
        sorter: (a: any, b: any) => a.active - b.active,
    },
    {
        title: 'Verdachtsfälle',
        dataIndex: 'suspicion',
        key: 'suspicion',
        width: 10,
        sorter: (a: any, b: any) => a.suspicion - b.suspicion,
    },
    {
        title: 'Genesene Fälle',
        dataIndex: 'recovered',
        key: 'recovered',
        width: 10,
        sorter: (a: any, b: any) => a.recovered - b.recovered,
    },
    {
        title: 'Verstorbene Fälle',
        dataIndex: 'deaths',
        key: 'deaths',
        width: 10,
        sorter: (a: any, b: any) => a.deaths - b.deaths,
    },
];
export const data_fill = [
    {
        zip: "12345",
        active: "320",
        suspicion: "742",
        recovered: "135",
        deaths: "12",
    },
    {
        zip: "12346",
        active: "920",
        suspicion: "342",
        recovered: "1435",
        deaths: "12",
    },
    {
        zip: "17458",
        active: "54",
        suspicion: "68",
        recovered: "125",
        deaths: "14",
    },
    {
        zip: "48569",
        active: "256",
        suspicion: "123",
        recovered: "48",
        deaths: "5",
    },
    {
        zip: "45369",
        active: "12",
        suspicion: "25",
        recovered: "2",
        deaths: "0",
    },
    {
        zip: "43985",
        active: "36",
        suspicion: "21",
        recovered: "3",
        deaths: "7",
    },
    {
        zip: "68524",
        active: "3425",
        suspicion: "1258",
        recovered: "125",
        deaths: "35",
    }
];