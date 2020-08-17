import React from 'react';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Input, Button, Form } from 'antd';
import { login } from './actions';
import { connect } from 'react-redux';

const layout = {
  labelCol: {
    span: 0,
  },
  wrapperCol: {
    span: 24,
  },
};

class LoginForm extends React.Component {
  onFinish = values => {
    this.props.login(values);
  };

  onFinishFailed = errorInfo => {
    console.log('Failed:', errorInfo);
  };

  render() {
    return (
      <Form
        {...layout}
        name="basic"
        initialValues={{
          remember: true,
        }}
        onFinish={this.onFinish}
        onFinishFailed={this.onFinishFailed}
      >
        <Form.Item
          name="username"
          rules={[
            {
              required: true,
              message: '帳號為必填!',
            },
          ]}
        >
          <Input prefix={<UserOutlined style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="帳號" />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[
            {
              required: true,
              message: '密碼為必填!',
            },
          ]}
        >
          <Input.Password prefix={<LockOutlined style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="密碼" />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" block>
            登入
          </Button>
        </Form.Item>
      </Form>
    );
  }
}

const mapStateToProps = () => ({});

const mapDispatchToProps = { login };

export default connect(mapStateToProps, mapDispatchToProps)(LoginForm);
