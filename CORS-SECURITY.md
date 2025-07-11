# CORS Security Configuration

## Overview
This document explains the CORS (Cross-Origin Resource Sharing) security configuration implemented in the Restaurant Service.

## Architecture
This service is designed to work within a microservices architecture with an API Gateway. The CORS configuration follows these principles:

1. **API Gateway First**: The primary CORS handling should be done at the API Gateway level
2. **Defense in Depth**: This service provides its own CORS configuration as a security fallback
3. **Environment-Specific**: Different CORS policies for different deployment environments

## Configuration

### Environment-Specific CORS Settings

#### Local Development (`application-local.yaml`)
- **Allowed Origins**: `http://localhost:3000,http://localhost:8080,http://localhost:9090,http://localhost:4200`
- **Purpose**: Supports local frontend development on common ports
- **Security Level**: Relaxed for development convenience

#### Development Environment (`application-dev.yaml`)
- **Allowed Origins**: `https://api-gateway.dev.company.com,http://localhost:8080`
- **Purpose**: Restricted to known development infrastructure
- **Security Level**: Moderate - allows API Gateway and limited local access

#### Production Environment
- **Recommendation**: CORS should be handled entirely by the API Gateway
- **Fallback**: Minimal CORS configuration for emergency access

### Configuration Properties

```yaml
app:
  cors:
    allowed-origins: "comma,separated,origins"
    allowed-methods: "GET,POST,PUT,DELETE,OPTIONS"
    allowed-headers: "*"
    allow-credentials: true
    max-age: 3600
```

## Security Best Practices Implemented

1. **No Wildcard Origins**: Never use `*` for allowed origins in production
2. **Specific Methods**: Only allow necessary HTTP methods
3. **Header Control**: Carefully control allowed headers
4. **Credentials Handling**: Properly configure credential sharing
5. **Cache Control**: Set appropriate max-age for preflight requests

## API Gateway Integration

### Recommended API Gateway CORS Configuration
```yaml
# Example for API Gateway
cors:
  allowed-origins:
    - "https://your-frontend-app.com"
    - "https://admin-panel.company.com"
  allowed-methods: ["GET", "POST", "PUT", "DELETE", "OPTIONS"]
  allowed-headers: ["Content-Type", "Authorization"]
  allow-credentials: true
  max-age: 86400
```

### Service-Level CORS (This Service)
- Acts as a fallback security layer
- Provides development support
- Handles internal service-to-service communication

## Security Considerations

### ✅ What's Secure
- Environment-specific origin restrictions
- Method-specific allowances
- Proper credential handling
- Configurable through properties

### ⚠️ Potential Risks
- Local development allows multiple localhost ports
- Wildcard headers in some environments
- Credentials enabled (necessary for authenticated requests)

### 🔒 Production Recommendations
1. Disable service-level CORS in production
2. Handle all CORS at API Gateway level
3. Use specific origins, never wildcards
4. Regularly audit allowed origins
5. Monitor CORS-related security logs

## Testing CORS Configuration

### Local Testing
```bash
# Test preflight request
curl -X OPTIONS http://localhost:9091/restaurant/fetchAllRestaurants \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: GET" \
  -v

# Test actual request
curl -X GET http://localhost:9091/restaurant/fetchAllRestaurants \
  -H "Origin: http://localhost:3000" \
  -v
```

### Expected Headers in Response
- `Access-Control-Allow-Origin`
- `Access-Control-Allow-Methods`
- `Access-Control-Allow-Headers`
- `Access-Control-Allow-Credentials`
- `Access-Control-Max-Age`

## Troubleshooting

### Common Issues
1. **CORS errors in browser**: Check if origin is in allowed list
2. **Preflight failures**: Verify OPTIONS method is allowed
3. **Credential issues**: Ensure `allow-credentials` is properly set
4. **Cache problems**: Check `max-age` settings

### Debug Mode
Add to `application.yaml` for debugging:
```yaml
logging:
  level:
    org.springframework.web.cors: DEBUG
```