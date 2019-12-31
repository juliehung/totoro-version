import React from 'react';
import moment from 'moment';
import { Table } from 'antd';

const headerData = [
  {
    key: '0',
    time: '時間',
    name: '病患名稱',
    mrn: '病編',
    birth: '生日',
    gender: '性別',
    phone: '電話',
    doctor: '預約醫師',
    note: '備註',
  },
];

export default class PrintAppList extends React.Component {
  render() {
    const style = {
      whiteSpace: 'nowrap',
      margin: 0,
      color: '#000',
    };
    const columns = [
      {
        title: '時間',
        dataIndex: 'time',
        key: 'time',
        render: time => <p style={style}>{time}</p>,
      },
      {
        title: '病患名稱',
        dataIndex: 'name',
        key: 'name',
        render: name => <p style={style}>{name}</p>,
      },
      {
        title: '病編',
        dataIndex: 'mrn',
        key: 'mrn',
        render: mrn => <p style={style}>{mrn}</p>,
      },
      {
        title: '生日',
        key: 'birth',
        dataIndex: 'birth',
        render: birth => {
          if (moment(birth, 'YYYY-MM-DD').isValid()) {
            const age = moment().diff(moment(birth, 'YYYY-MM-DD'), 'years');
            return (
              <p style={style}>
                {moment(birth)
                  .add(-1911, 'year')
                  .format('YYYY-MM-DD')
                  .replace(/^0+/, '')}{' '}
                ({age})
              </p>
            );
          } else {
            return <p style={style}>{birth}</p>;
          }
        },
      },
      {
        title: '性別',
        dataIndex: 'gender',
        key: 'gender',
        render: gender => <p style={style}>{gender === 'MALE' ? '男' : gender === 'FEMALE' ? '女' : gender}</p>,
      },
      {
        title: '電話',
        dataIndex: 'phone',
        key: 'phone',
        render: phone => <p style={style}>{phone}</p>,
      },
      {
        title: '預約醫師',
        dataIndex: 'doctor',
        key: 'doctor',
        render: doctor => <p style={style}>{doctor}</p>,
      },
      {
        title: '備註',
        dataIndex: 'note',
        key: 'note',
        render: note => <span style={{ color: '#000' }}>{note}</span>,
      },
    ];

    return (
      <div>
        <div>
          <div
            style={{
              display: 'flex',
              justifyContent: 'space-between',
              marginBottom: '20px',
              fontSize: '18px',
              fontWeight: 'bold',
              color: '#000',
            }}
          >
            <span>{this.props.clinicName}</span>
            <span>預約表</span>
            <span>
              日期:
              {moment(this.props.date)
                .add(-1911, 'y')
                .format('YYYY-MM-DD')
                .replace(/^0+/, '')}
            </span>
          </div>
          <Table
            columns={columns}
            dataSource={[...headerData, ...this.props.appointmentList]}
            pagination={false}
            bordered
            showHeader={false}
          />
        </div>
        {this.props.doctorList &&
          this.props.doctorList.length > 1 &&
          this.props.doctorList.map(d => {
            return (
              <div key={d}>
                <div
                  style={{
                    display: 'flex',
                    justifyContent: 'space-between',
                    marginBottom: '20px',
                    fontSize: '18px',
                    fontWeight: 'bold',
                    pageBreakBefore: 'always',
                    color: '#000',
                  }}
                >
                  <span>{this.props.clinicName}</span>
                  <span>預約表</span>
                  <span>
                    日期:
                    {moment(this.props.date)
                      .add(-1911, 'y')
                      .format('YYYY-MM-DD')
                      .replace(/^0+/, '')}
                  </span>
                </div>
                <Table
                  columns={columns}
                  dataSource={[
                    ...headerData.map(header => ({
                      ...header,
                      key: `${header.key}${d}`,
                    })),
                    ...this.props.appointmentList.filter(a => a.doctor === d).map(a => ({ ...a, key: `${a.key}${d}` })),
                  ]}
                  pagination={false}
                  bordered
                  showHeader={false}
                />
              </div>
            );
          })}
      </div>
    );
  }
}
